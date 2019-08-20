package oleh_bendarskyi.intership_project.questionnaire.context;

import lombok.Data;
import oleh_bendarskyi.intership_project.questionnaire.models.QuestionnaireField;
import oleh_bendarskyi.intership_project.questionnaire.models.Type;
import oleh_bendarskyi.intership_project.questionnaire.service.QuestionnaireFieldService;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;
import oleh_bendarskyi.intership_project.questionnaire.service.QuestionnaireResponseService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;
import static oleh_bendarskyi.intership_project.questionnaire.utils.Constants.OPTIONS_SPLIT_REGEX;

@Data
@ManagedBean(name = Constants.QUESTIONNAIRE_CONTEXT)
@ApplicationScoped
public class QuestionnaireContext implements Serializable {

    private List<Map<String, Object>> records;

    private Map<String, QuestionnaireField> questionnaireFields;

    private List<Type> types;

    @ManagedProperty(value = "#{" + Constants.QUESTIONNAIRE_RESPONSE_SERVICE + "}")
    private QuestionnaireResponseService questionnaireResponseService;

    @ManagedProperty(value = "#{" + Constants.QUESTIONNAIRE_FIELD_SERVICE + "}")
    private QuestionnaireFieldService questionnaireFieldService;

    @PostConstruct
    public void init() {
        updateRecords();
        types = new ArrayList<>(Arrays.asList(Type.values()));
        List<QuestionnaireField> fields = questionnaireFieldService.getAll();
        questionnaireFields = fields
                .stream()
                .peek(x -> x.setOptions(x.getOptions() == null ? StringUtils.EMPTY : x.getOptions()
                        .replaceAll(OPTIONS_SPLIT_REGEX, lineSeparator())))
                .collect(Collectors.toMap(x -> x.getId().toString(), x -> x));
    }

    public void updateRecords(){
        records = questionnaireResponseService.getAll();
    }

}
