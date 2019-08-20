package oleh_bendarskyi.intership_project.questionnaire.dao;

import oleh_bendarskyi.intership_project.questionnaire.models.QuestionnaireField;
import org.hibernate.Session;

import java.util.List;

public interface QuestionnaireFieldDao {
    QuestionnaireField create(QuestionnaireField questionnaireField, Session session);
    QuestionnaireField findById(long id, Session session);
    void update(QuestionnaireField questionnaireField, Session session);
    QuestionnaireField delete(QuestionnaireField questionnaireField, Session session);
    List<QuestionnaireField> getAll(Session session);
    long getNextIdentity(Session session);
}
