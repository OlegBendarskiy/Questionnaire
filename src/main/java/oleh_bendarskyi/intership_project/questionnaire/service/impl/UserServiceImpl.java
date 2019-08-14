package oleh_bendarskyi.intership_project.questionnaire.service.impl;

import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;
import oleh_bendarskyi.intership_project.questionnaire.dao.UserDao;
import oleh_bendarskyi.intership_project.questionnaire.dao.impl.UserDaoImpl;
import oleh_bendarskyi.intership_project.questionnaire.models.User;
import oleh_bendarskyi.intership_project.questionnaire.service.UserService;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name= Constants.USER_SERVICE)
@ApplicationScoped
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
        return dao.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    public boolean update(User updatedUser) {
        return dao.update(updatedUser);
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
