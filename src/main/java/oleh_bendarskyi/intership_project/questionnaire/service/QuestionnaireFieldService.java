package oleh_bendarskyi.intership_project.questionnaire.service;

import oleh_bendarskyi.intership_project.questionnaire.models.QuestionnaireField;

import java.util.List;

public interface QuestionnaireFieldService {
    QuestionnaireField create(QuestionnaireField questionnaireField);
    QuestionnaireField findById(long id);
    boolean update(QuestionnaireField oldField, QuestionnaireField questionnaireField);
    QuestionnaireField delete(QuestionnaireField questionnaireField);
    List<QuestionnaireField> getAll();
    long getNextIdentity();
}
