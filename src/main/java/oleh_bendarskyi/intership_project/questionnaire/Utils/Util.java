package oleh_bendarskyi.intership_project.questionnaire.Utils;

import oleh_bendarskyi.intership_project.questionnaire.beans.ChangePasswordBean;
import oleh_bendarskyi.intership_project.questionnaire.beans.EditProfileBean;
import oleh_bendarskyi.intership_project.questionnaire.beans.SignUpBean;
import oleh_bendarskyi.intership_project.questionnaire.models.User;
import org.apache.commons.lang3.SerializationUtils;

public class Util {

    public static User convertSignUpBean(SignUpBean bean) {
        String phoneNumber = getPhoneNumber(bean.getPhoneNumber());
        return new User((long) 0, bean.getFirstName(), bean.getLastName(), phoneNumber, bean.getEmail(), bean.getPassword());
    }

    public static User convertEditProfileBean(EditProfileBean bean, User user) {
        String phoneNumber = getPhoneNumber(bean.getPhoneNumber());
        User updatedUser = SerializationUtils.clone(user);
        updatedUser.setFirstName(bean.getFirstName());
        updatedUser.setLastName(bean.getLastName());
        updatedUser.setEmail(bean.getEmail());
        updatedUser.setPhoneNumber(phoneNumber);
        return updatedUser;
    }

    private static String getPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("[\\s()-]", "");
    }

    public static User convertChangePasswordBean(ChangePasswordBean bean, User user) {
        User updatedUser = SerializationUtils.clone(user);
        updatedUser.setPassword(bean.getNewPassword());
        return updatedUser;
    }
}
