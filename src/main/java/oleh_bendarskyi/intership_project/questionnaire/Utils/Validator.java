package oleh_bendarskyi.intership_project.questionnaire.Utils;

import oleh_bendarskyi.intership_project.questionnaire.models.LogInBean;
import oleh_bendarskyi.intership_project.questionnaire.models.SignUpBean;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Validator {

    private static final String NAME_REGEXP = "^[A-ZА-ЯЁЪ]{1}[a-zа-яёъ]+$";
    private static final String PHONE_REGEXP = "^[+]38 \\(\\d{3}\\) \\d{3}-\\d{4}$";
    private static final String EMAIL_REGEXP = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    private static final String PASSWORD_REGEXP = "^[a-zA-Z].{5,20}$";
    private static final String NULL_FIELD = "Field shouldn\'t be empty";

    private Validator() {
    }

    public static Map<String, String> validateSignUpForm(SignUpBean bean) {
        Map<String, String> errors = new HashMap<>();
        validateEmail(bean.getEmail(), errors);
        validatePasswords(bean.getPassword(), bean.getConfirmPassword(), errors);
        validateName(bean.getFirstName(), "firstName", errors);
        validateName(bean.getLastName(), "lastName", errors);
        validatePhoneNumber(bean.getPhoneNumber(), errors);
        return errors;
    }

    private static void validateName(String name, String inputName, Map<String, String> errors) {
        if (isNotBlank(name) && Validator.validate(name, NAME_REGEXP)) {
            errors.put(inputName, "Use letter only and starting with upper letter");
        }
    }

    private static boolean validate(String string, String regexp) {
        if (string == null || regexp == null || string.trim().length() == 0) {
            return true;
        }
        return !string.matches(regexp);
    }

    private static void validatePhoneNumber(String phoneNumber, Map<String, String> errors) {
        if (isNotBlank(phoneNumber) && Validator.validate(phoneNumber, PHONE_REGEXP)) {
            errors.put("phoneNumber", "Input correct phone number.");
        }
    }

    private static void validateEmail(String email, Map<String, String> errors) {
        if (isBlank(email)) {
            errors.put("email", NULL_FIELD);
        } else if (Validator.validate(email, EMAIL_REGEXP)) {
            errors.put("email", "Enter correct email");
        }
    }

    private static void validatePasswords(String password, String confirmPassword, Map<String, String> errors) {
        if (isBlank(password)) {
            errors.put("password", NULL_FIELD);
            errors.put("confirmPassword", "Incorrect password");
        } else if (!password.matches(PASSWORD_REGEXP)) {
            errors.put("password", "Use 6-20 simbols and starting with letter.");
            errors.put("confirmPassword", "Incorrect password");
        } else if (isBlank(password)) {
            errors.put("confirmPassword", NULL_FIELD);
        } else if (!password.equals(confirmPassword)) {
            errors.put("confirmPassword", "Passwords do not match");
        }
    }

    public static Map<String, String> validateLogInFormBean(LogInBean bean) {
        Map<String, String> errors = new HashMap<>();
        validateEmail(bean.getEmail(), errors);
        if(isBlank(bean.getPassword())){
            errors.put("password", NULL_FIELD);
        }
        return errors;
    }
}
