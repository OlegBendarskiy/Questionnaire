package oleh_bendarskyi.intership_project.questionnaire;

import oleh_bendarskyi.intership_project.questionnaire.Utils.HibernateUtil;
import oleh_bendarskyi.intership_project.questionnaire.dao.UserDao;
import oleh_bendarskyi.intership_project.questionnaire.dao.impl.UserDaoImpl;
import oleh_bendarskyi.intership_project.questionnaire.models.User;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            UserDao dao = new UserDaoImpl();
            User user = dao.findByEmail("aaa@mail.com");
            System.out.println(user);
            /*System.out.println("11111111111");
            User user = dao.findById(1);
            System.out.println(user);

            System.out.println("22222222222");
            List<User> users = dao.getAll();
            assert users != null;
            System.out.println(users.size());
            for (User u:users) {
                System.out.println(u);
            }

            System.out.println("3333333333");
            user = dao.create(new User((long) 2, "Bbb", "Bbb", "+388888888888", "bbb@email.com", "bbb"));
            System.out.println(user);

            System.out.println("3333333333");
            user = dao.findById(1);
            user.setFirstName("aaaChanged");
            user = dao.update(user);
            System.out.println(user);

            System.out.println("44444444444444");
            users = dao.getAll();
            assert users != null;
            System.out.println(users.size());
            for (User u:users) {
                System.out.println(u);
            }*/
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
