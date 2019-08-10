package oleh_bendarskyi.intership_project.questionnaire.Utils;

import oleh_bendarskyi.intership_project.questionnaire.models.SignUpBean;
import oleh_bendarskyi.intership_project.questionnaire.models.User;

import javax.faces.context.FacesContext;
import javax.persistence.GenerationType;

public class Util {

    public static User convertSignUpBean(SignUpBean bean) {
        String phoneNumber = bean.getPhoneNumber().replaceAll("[\\s()-]", "");
        System.out.println(phoneNumber.length() + "||||||||||||||||");
        return new User((long) 100, bean.getFirstName(), bean.getLastName(), phoneNumber, bean.getEmail(), bean.getPassword());
    }
}
