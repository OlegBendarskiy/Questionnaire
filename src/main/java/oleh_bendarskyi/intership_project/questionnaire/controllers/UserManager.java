package oleh_bendarskyi.intership_project.questionnaire.controllers;

import lombok.Data;
import oleh_bendarskyi.intership_project.questionnaire.Utils.Util;
import oleh_bendarskyi.intership_project.questionnaire.Utils.Validator;
import oleh_bendarskyi.intership_project.questionnaire.beans.ChangePasswordBean;
import oleh_bendarskyi.intership_project.questionnaire.beans.EditProfileBean;
import oleh_bendarskyi.intership_project.questionnaire.beans.LogInBean;
import oleh_bendarskyi.intership_project.questionnaire.beans.SignUpBean;
import oleh_bendarskyi.intership_project.questionnaire.exeptions.UserWithTisEmailIsAlreadyExistException;
import oleh_bendarskyi.intership_project.questionnaire.models.User;
import oleh_bendarskyi.intership_project.questionnaire.service.UserService;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@ManagedBean(name = "user", eager = true)
@SessionScoped
public class UserManager implements Serializable {

    private static final Logger LOGGER =
            Logger.getLogger(UserManager.class);
    public static final String INVALID_INPUT = "INVALID INPUT!";
    public static final String ERROR_CHANGING_PASSWORD = "ERROR CHANGING PASSWORD!";
    public static final String NO_CHANGES_DETECTED = "NO CHANGES DETECTED!";
    public static final String PASSWORD_UPDATED_SUCCESSFULLY = "PASSWORD UPDATED SUCCESSFULLY!";
    public static final String PROFILE_UPDATED_SUCCESSFULLY = "PROFILE UPDATED SUCCESSFULLY!";
    public static final String ERROR_EDITING_USER = "ERROR EDITING USER!";
    private Map<String, String> errors;
    private User user;
    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    public void performLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        LogInBean bean = (LogInBean) context.getExternalContext().getRequestMap().get("loginBean");
        errors = Validator.validateLogInFormBean(bean);
        if (!errors.isEmpty()) {
            String s = errors.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_INPUT, s));
            return;
        }

        user = userService.findByEmail(bean.getEmail());
        if (user != null && user.getPassword().equals(bean.getPassword())) {
            redirect("/webapp/");
        } else {
            String s = "USER IS NOT FOUND!";
            LOGGER.info(s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, s, null));
        }
    }

    public void logout() {
        long id = user.getId();

        // invalidate the session
        LOGGER.debug("invalidating session for user with id: " + id);
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        LOGGER.info("logout successful for user with id: " + id);
        redirect("/webapp/");
    }

    public void performRegistration() {
        FacesContext context = FacesContext.getCurrentInstance();
        SignUpBean bean = (SignUpBean) context.getExternalContext().getRequestMap().get("signUpBean");
        errors = Validator.validateSignUpForm(bean);
        if (!errors.isEmpty()) {
            String s = errors.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_INPUT, s));
            return;
        }
        User newUser = Util.convertSignUpBean(bean);
        if (isExistingEmail(newUser)) {
            String message = "User with that email already exists";
            errors.put("email", "User with that email already exists");
            LOGGER.info(INVALID_INPUT + message);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_INPUT, message));
            return;
        }
        user = userService.create(newUser);
        redirect("/webapp/");
    }

    public void editProfile() {
        FacesContext context = FacesContext.getCurrentInstance();
        EditProfileBean bean = (EditProfileBean) context.getExternalContext().getRequestMap().get("editProfileBean");
        errors = Validator.validateEditProfileForm(bean);
        if (!errors.isEmpty()) {
            String s = errors.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_INPUT, s));
            return;
        }

        User updatedUser = Util.convertEditProfileBean(bean, user);
        if (isExistingEmail(updatedUser)) {
            String message = "User with that email already exists";
            errors.put("email", "User with that email already exists");
            LOGGER.info(INVALID_INPUT + message);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_INPUT, message));
            return;
        }

        if (user.equals(updatedUser)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, NO_CHANGES_DETECTED, null));
        } else if (userService.update(updatedUser)) {
            user = updatedUser;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, PROFILE_UPDATED_SUCCESSFULLY, null));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_EDITING_USER, null));
        }
    }

    private boolean isExistingEmail(User updatedUser) {
        User dbUser = userService.findByEmail(updatedUser.getEmail());
        return dbUser != null && !dbUser.getId().equals(updatedUser.getId());
    }

    public void changePassword() {
        FacesContext context = FacesContext.getCurrentInstance();
        ChangePasswordBean bean = (ChangePasswordBean) context.getExternalContext().getRequestMap().get("changePasswordBean");
        errors = Validator.validateChangePasswordForm(bean);
        if (!errors.isEmpty()) {
            String s = errors.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_INPUT, s));
            return;
        }
        User updatedUser = Util.convertChangePasswordBean(bean, user);
        User dbUser = userService.findById(updatedUser.getId());
        System.out.println(":::::::::::::::::::"+dbUser);
        if (user.getPassword().equals(updatedUser.getPassword())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, NO_CHANGES_DETECTED, null));
        } else if (dbUser.getPassword().equals(bean.getCurrentPassword())) {
            if (userService.update(updatedUser)) {
                user = updatedUser;
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, PASSWORD_UPDATED_SUCCESSFULLY, null));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_CHANGING_PASSWORD, null));
            }
        } else {
            String message = "You entered wrong user password";
            errors.put("currentPassword", message);
            LOGGER.info(ERROR_CHANGING_PASSWORD + message);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_CHANGING_PASSWORD, message));
        }
    }


    public void clearErrors() {
        errors = null;
    }


    private void redirect(String path) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        try {
            extContext.redirect(path);
        } catch (IOException e) {
            LOGGER.error("Cannot redirect to: " + path);
        }
    }


}
