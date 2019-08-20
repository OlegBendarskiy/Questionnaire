package oleh_bendarskyi.intership_project.questionnaire.dao.impl;

import oleh_bendarskyi.intership_project.questionnaire.dao.QuestionnaireFieldDao;
import oleh_bendarskyi.intership_project.questionnaire.models.QuestionnaireField;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

public class QuestionnaireFieldDaoImpl implements QuestionnaireFieldDao {
    private static final Logger LOGGER =
            Logger.getLogger(QuestionnaireFieldDaoImpl.class);

    @Override
    public QuestionnaireField create(QuestionnaireField questionnaireField, Session session) {
        long id = (Long) session.save(questionnaireField);
        questionnaireField.setId(id);
        return questionnaireField;
    }

    @Override
    public QuestionnaireField findById(long id, Session session) {
        return session.get(QuestionnaireField.class, id);
    }

    @Override
    public void update(QuestionnaireField questionnaireField, Session session) {
        session.update(questionnaireField);
    }

    @Override
    public QuestionnaireField delete(QuestionnaireField questionnaireField, Session session) {
        session.delete(questionnaireField);
        return questionnaireField;
    }

    @Override
    public List<QuestionnaireField> getAll(Session session) {
        return session.createQuery("from QuestionnaireField", QuestionnaireField.class).list();
    }

    @Override
    public long getNextIdentity(Session session) {
        Object o = session.createQuery("SELECT COUNT(id) from QuestionnaireField questionnaireField").getSingleResult();
        if(o != null && (long)o!= 0){
            return (long) session.createQuery("SELECT MAX(id) from QuestionnaireField questionnaireField").getSingleResult() +1;
        }
        return 1;
    }
}
