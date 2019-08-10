package oleh_bendarskyi.intership_project.questionnaire.service.impl;

import oleh_bendarskyi.intership_project.questionnaire.dao.UserDao;
import oleh_bendarskyi.intership_project.questionnaire.dao.impl.UserDaoImpl;
import oleh_bendarskyi.intership_project.questionnaire.models.User;
import oleh_bendarskyi.intership_project.questionnaire.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao dao;

    public UserServiceImpl() {
        dao = new UserDaoImpl();
    }

    @Override
    public User create(User user) {
        return dao.create(user);
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User delete(User user) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
