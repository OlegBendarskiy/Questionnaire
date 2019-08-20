package oleh_bendarskyi.intership_project.questionnaire.service.impl;

import oleh_bendarskyi.intership_project.questionnaire.dao.QuestionnaireFieldDao;
import oleh_bendarskyi.intership_project.questionnaire.dao.QuestionnaireResponseDao;
import oleh_bendarskyi.intership_project.questionnaire.dao.impl.QuestionnaireFieldDaoImpl;
import oleh_bendarskyi.intership_project.questionnaire.dao.impl.QuestionnaireResponseDaoImpl;
import oleh_bendarskyi.intership_project.questionnaire.models.QuestionnaireField;
import oleh_bendarskyi.intership_project.questionnaire.service.QuestionnaireResponseService;
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
import java.util.Map;
import java.util.stream.Collectors;

@ManagedBean(name = Constants.QUESTIONNAIRE_RESPONSE_SERVICE)
@ApplicationScoped
public class QuestionnaireResponseServiceImpl implements QuestionnaireResponseService {
    private static final Logger LOGGER =
            Logger.getLogger(QuestionnaireFieldDaoImpl.class);
    private QuestionnaireResponseDao dao;
    private QuestionnaireFieldDao fieldsDao;
    private SessionFactory factory;

    public QuestionnaireResponseServiceImpl() {
        factory = HibernateUtil.getSessionFactory();
        dao = new QuestionnaireResponseDaoImpl();
        fieldsDao = new QuestionnaireFieldDaoImpl();
    }


    @Override
    public boolean create(Map<String, Object> questionnaireRecord) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            dao.create(questionnaireRecord, session);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error creating response: " + questionnaireRecord.keySet().stream()
                    .map(key -> key + "=" + questionnaireRecord.get(key))
                    .collect(Collectors.joining(", ")), e);
            return false;
        }
        return true;
    }

    @Override
    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> res = null;
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            List<String> fields = fieldsDao.getAll(session).stream().map(QuestionnaireField::getLabel).collect(Collectors.toList());
            res = dao.getAll(fields, session);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error finding all responses", e);
        }
        return res;
    }
}
