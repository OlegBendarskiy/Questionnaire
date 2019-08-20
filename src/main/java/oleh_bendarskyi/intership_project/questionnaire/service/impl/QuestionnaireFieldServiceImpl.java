package oleh_bendarskyi.intership_project.questionnaire.service.impl;

import oleh_bendarskyi.intership_project.questionnaire.dao.QuestionnaireFieldDao;
import oleh_bendarskyi.intership_project.questionnaire.dao.QuestionnaireResponseDao;
import oleh_bendarskyi.intership_project.questionnaire.dao.impl.QuestionnaireFieldDaoImpl;
import oleh_bendarskyi.intership_project.questionnaire.dao.impl.QuestionnaireResponseDaoImpl;
import oleh_bendarskyi.intership_project.questionnaire.models.QuestionnaireField;
import oleh_bendarskyi.intership_project.questionnaire.service.QuestionnaireFieldService;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;
import oleh_bendarskyi.intership_project.questionnaire.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = Constants.QUESTIONNAIRE_FIELD_SERVICE)
@ApplicationScoped
public class QuestionnaireFieldServiceImpl implements QuestionnaireFieldService {
    private QuestionnaireResponseDao responseDao;
    private QuestionnaireFieldDao dao;
    private SessionFactory factory;
    private static final Logger LOGGER =
            Logger.getLogger(QuestionnaireFieldServiceImpl.class);


    public QuestionnaireFieldServiceImpl() {
        factory = HibernateUtil.getSessionFactory();
        responseDao = new QuestionnaireResponseDaoImpl();
        dao = new QuestionnaireFieldDaoImpl();
    }

    @Override
    public QuestionnaireField create(QuestionnaireField questionnaireField) {
        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            responseDao.createColumn("\"" + questionnaireField.getLabel() + "\"", session);
            questionnaireField = dao.create(questionnaireField, session);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error creating questionnaire field: " + questionnaireField, e);
        }
        return questionnaireField;
    }

    @Override
    public QuestionnaireField findById(long id) {
        Transaction tx = null;
        QuestionnaireField questionnaireField = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            questionnaireField = dao.findById(id, session);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error finding questionnaire field with id: " + id, e);
        }
        return questionnaireField;
    }

    @Override
    public boolean update(QuestionnaireField oldField, QuestionnaireField updatedField) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            String newLabel = "\"" + updatedField.getLabel() + "\"";
            String oldLabel = "\"" + oldField.getLabel() + "\"";
            dao.update(updatedField, session);
            if (!oldLabel.equals(newLabel)) {
                responseDao.updateColumn(oldLabel, newLabel, session);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error updating questionnaire field with id: " + updatedField.getId(), e);
            return false;
        }
        return true;
    }

    @Override
    public QuestionnaireField delete(QuestionnaireField questionnaireField) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            responseDao.deleteColumn("\"" + questionnaireField.getLabel() + "\"", session);
            questionnaireField = dao.delete(questionnaireField, session);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error deleting questionnaire field with id: " + questionnaireField.getId(), e);
        }
        return questionnaireField;
    }

    @Override
    public List<QuestionnaireField> getAll() {
        List<QuestionnaireField> questionnaireFields = null;
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            questionnaireFields = dao.getAll(session);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error finding all questionnaire fields", e);
        }
        return questionnaireFields;
    }

    @Override
    public long getNextIdentity() {
        long id = 0;

        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            id = dao.getNextIdentity(session);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error finding next id in questionnaire fields", e);
        }
        return id;
    }
}
