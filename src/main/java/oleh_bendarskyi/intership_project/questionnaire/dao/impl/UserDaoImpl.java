package oleh_bendarskyi.intership_project.questionnaire.dao.impl;

import oleh_bendarskyi.intership_project.questionnaire.utils.HibernateUtil;

import oleh_bendarskyi.intership_project.questionnaire.dao.UserDao;
import oleh_bendarskyi.intership_project.questionnaire.models.User;
import org.apache.log4j.Logger;
import org.hibernate.*;


import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER =
            Logger.getLogger(UserDaoImpl.class);

    private SessionFactory factory;

    public UserDaoImpl() {
        this.factory = HibernateUtil.getSessionFactory();
    }

    @Override
    public User create(User user) {
        Transaction tx = null;
        Long id;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            id = (Long) session.save(user);
            user.setId(id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error creating user: " + user, e);
        }
        return user;
    }

    @Override
    public User findById(long id) {
        Transaction tx = null;
        User user = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            user = session.get(User.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error finding user with id: " + id, e);
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        Transaction tx = null;
        User user = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            TypedQuery<User> query = session.createQuery("from User where email=:email", User.class);
            query.setParameter("email", email);
            try {
                user = query.getSingleResult();
            } catch (NoResultException nre) {
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error finding user with email: " + email, e);

        }
        return user;
    }

    @Override
    public boolean update(User user) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error updating user with id: " + user.getId(), e);
            return false;
        }
        return true;
    }

    @Override
    public User delete(User user) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error deleting user with id: " + user.getId(), e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = null;
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            LOGGER.error("Error finding all users", e);
        }
        return users;
    }
}
