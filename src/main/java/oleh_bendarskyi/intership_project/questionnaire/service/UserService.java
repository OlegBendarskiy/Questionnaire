package oleh_bendarskyi.intership_project.questionnaire.service;

import oleh_bendarskyi.intership_project.questionnaire.models.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User findById(long id);
    User findByEmail(String email);
    User update(User user);
    User delete(User user);
    List<User> getAll();
}
