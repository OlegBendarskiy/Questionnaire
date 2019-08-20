package oleh_bendarskyi.intership_project.questionnaire.controllers;

import lombok.Data;
import oleh_bendarskyi.intership_project.questionnaire.context.QuestionnaireContext;
import oleh_bendarskyi.intership_project.questionnaire.models.QuestionnaireField;
import oleh_bendarskyi.intership_project.questionnaire.service.QuestionnaireFieldService;
import oleh_bendarskyi.intership_project.questionnaire.service.QuestionnaireResponseService;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;
import oleh_bendarskyi.intership_project.questionnaire.utils.Validator;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;

@Data
@ManagedBean(name = Constants.QUESTIONNAIRE, eager = true)
@ViewScoped
public class QuestionnaireManager implements Serializable {

    private static final Logger LOGGER =
            Logger.getLogger(QuestionnaireManager.class);
    private Map<String, String> errors;
    private QuestionnaireField questionnaireField;
    @ManagedProperty(value = "#{" + Constants.QUESTIONNAIRE_FIELD_SERVICE + "}")
    private QuestionnaireFieldService questionnaireFieldService;
    @ManagedProperty(value = "#{" + Constants.QUESTIONNAIRE_RESPONSE_SERVICE + "}")
    private QuestionnaireResponseService questionnaireResponseService;

    @ManagedProperty(value = "#{" + Constants.QUESTIONNAIRE_CONTEXT + "}")
    private QuestionnaireContext questionnaireContext;
    private Map<String, Object> questionnaireRecord;
    private boolean success = false;


    @PostConstruct
    public void init() {

        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(Constants.QUESTIONNAIRE_FIELD_ID);

        if (id != null && !questionnaireContext.getQuestionnaireFields().containsKey(id)) {
            questionnaireField = new QuestionnaireField();
            questionnaireField.setId(Long.valueOf(id));
        } else {
            questionnaireField = SerializationUtils.clone(questionnaireContext.getQuestionnaireFields().get(id));
        }
        questionnaireRecord = questionnaireContext.getQuestionnaireFields().values().stream()
                .collect(Collectors.toMap(QuestionnaireField::getLabel, x -> StringUtils.EMPTY));
    }

    public List<String> getSplittedOptions(String options) {
        return Arrays.asList(options.split(lineSeparator()));
    }

    public void getFieldDialog(String command) {
        FacesContext context = FacesContext.getCurrentInstance();
        String id = null;
        if (command.equals("add")) {
            long idLong = questionnaireFieldService.getNextIdentity();
            id = idLong < 1 ? null : String.valueOf(idLong);
        } else if (command.equals("edit")) {
            id = context.getExternalContext().getRequestParameterMap().get(Constants.QUESTIONNAIRE_FIELD_ID);
        }
        if (id != null) {
            redirect(Constants.FIELDS_PATH + "/" + id);
        } else {
            LOGGER.error("Error!");
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR!", null));
        }
    }

    public void submit() {
        FacesContext context = FacesContext.getCurrentInstance();
        for (Map.Entry<String, Object> s : questionnaireRecord.entrySet()) {
            if (s.getValue() instanceof Date) {
                Date date = (Date) s.getValue();
                SimpleDateFormat formatter = new SimpleDateFormat(Constants.MMM_DD_YYYY);
                String strDate = formatter.format(date);
                questionnaireRecord.put(s.getKey(), strDate);
            }
        }
        errors = Validator.validateQuestionnaireForm(questionnaireRecord, questionnaireContext.getQuestionnaireFields());
        if (!errors.isEmpty()) {
            String s = errors.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(Constants.INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, s));
            return;
        }
        boolean isSaved = questionnaireResponseService.create(questionnaireRecord);
        if (isSaved) {
            questionnaireContext.getRecords().add(questionnaireRecord);
            LOGGER.info(Constants.RESPONSE_SAVED_SUCCESSFULLY + " " + questionnaireRecord.keySet().stream()
                    .map(key -> key + "=" + questionnaireRecord.get(key))
                    .collect(Collectors.joining(", ")));
            success = true;
        } else {
            LOGGER.error(Constants.ERROR_SAVING_RESPONSE);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.ERROR_SAVING_RESPONSE, null));
        }
    }

    public void closeDialog() {
        redirect(Constants.FIELDS_PATH);
    }

    public void saveDialog() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean isSuccess;
        questionnaireField.setOptions(questionnaireField.getOptions().replaceAll(lineSeparator(), ", "));
        if (questionnaireContext.getQuestionnaireFields().get(String.valueOf(questionnaireField.getId())) != null) {
            isSuccess = questionnaireFieldService.update(questionnaireContext.getQuestionnaireFields().get(String.valueOf(questionnaireField.getId())), questionnaireField);
        } else {
            isSuccess = questionnaireFieldService.create(questionnaireField) != null;
        }

        if (isSuccess) {
            questionnaireContext.getQuestionnaireFields().put(String.valueOf(questionnaireField.getId()), questionnaireField);
            questionnaireContext.updateRecords();
            LOGGER.info(Constants.FIELD_UPDATED_SUCCESSFULLY + " Field id = " + questionnaireField.getId());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.FIELD_UPDATED_SUCCESSFULLY, null));
            redirect(Constants.FIELDS_PATH);
        } else {
            LOGGER.error("Error editing fields with id = " + questionnaireField.getId());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR EDITING FIELD!", "field id = " + questionnaireField.getId()));
        }
    }

    public void deleteField(String id) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (questionnaireFieldService.delete(questionnaireField) == null) {
            LOGGER.error("Error editing fields with id = " + questionnaireField.getId());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR DELETING FIELD!", "field id = " + questionnaireField.getId()));
            return;
        }
        questionnaireContext.getQuestionnaireFields().remove(id);
        questionnaireContext.updateRecords();
        LOGGER.info(Constants.FIELD_UPDATED_SUCCESSFULLY + " Field id = " + questionnaireField.getId());
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.FIELD_UPDATED_SUCCESSFULLY, null));
        redirect(Constants.FIELDS_PATH);
    }

    private void redirect(String path) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        String webappName = extContext.getApplicationContextPath();

        try {
            extContext.redirect(webappName + path);
        } catch (IOException e) {
            LOGGER.error("Cannot redirect to: " + path);
        }
    }

}
