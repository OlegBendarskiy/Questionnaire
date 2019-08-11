package oleh_bendarskyi.intership_project.questionnaire.dao;

import oleh_bendarskyi.intership_project.questionnaire.exeptions.UserWithTisEmailIsAlreadyExistException;
import oleh_bendarskyi.intership_project.questionnaire.models.User;

import java.util.List;

public interface UserDao {
    User create(User user);
    User findById(long id);
    User findByEmail(String email);
    boolean update(User user);
    User delete(User user);
    List<User> getAll();
}
