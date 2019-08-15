package oleh_bendarskyi.intership_project.questionnaire.controllers;

import lombok.Data;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;
import oleh_bendarskyi.intership_project.questionnaire.utils.EmailNotificator;
import oleh_bendarskyi.intership_project.questionnaire.utils.Util;
import oleh_bendarskyi.intership_project.questionnaire.utils.Validator;
import oleh_bendarskyi.intership_project.questionnaire.beans.ChangePasswordBean;
import oleh_bendarskyi.intership_project.questionnaire.beans.EditProfileBean;
import oleh_bendarskyi.intership_project.questionnaire.beans.LogInBean;
import oleh_bendarskyi.intership_project.questionnaire.beans.SignUpBean;
import oleh_bendarskyi.intership_project.questionnaire.models.User;
import oleh_bendarskyi.intership_project.questionnaire.service.UserService;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@ManagedBean(name = Constants.USER_MANAGER, eager = true)
@SessionScoped
public class UserManager implements Serializable {

    private static final Logger LOGGER =
            Logger.getLogger(UserManager.class);
    private Map<String, String> errors;
    private User user;
    @ManagedProperty(value = "#{"+ Constants.USER_SERVICE +"}")
    private UserService userService;

    public void performLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        LogInBean bean = (LogInBean) context.getExternalContext().getRequestMap().get(Constants.LOGIN_BEAN);
        errors = Validator.validateLogInFormBean(bean);
        if (!errors.isEmpty()) {
            String s = errors.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(Constants.INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, s));
            return;
        }

        user = userService.findByEmail(bean.getEmail());
        if (user != null && user.getPassword().equals(bean.getPassword())) {
            redirect(Constants.HOME_PATH);
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
        redirect(Constants.HOME_PATH);
    }

    public void performRegistration() {
        FacesContext context = FacesContext.getCurrentInstance();
        SignUpBean bean = (SignUpBean) context.getExternalContext().getRequestMap().get(Constants.SIGN_UP_BEAN);
        errors = Validator.validateSignUpForm(bean);
        if (!errors.isEmpty()) {
            String s = errors.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(Constants.INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, s));
            return;
        }
        User newUser = Util.convertSignUpBean(bean);
        if (isExistingEmail(newUser)) {
            String message = "User with that email already exists";
            errors.put("email", "User with that email already exists");
            LOGGER.info(Constants.INVALID_INPUT + message);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, message));
            return;
        }
        user = userService.create(newUser);
        try {
            EmailNotificator.sendSignUpNotification(newUser);
        } catch (MessagingException e) {
            LOGGER.error("Error email notification", e);
        }
        redirect(Constants.HOME_PATH);
    }

    public void editProfile() {
        FacesContext context = FacesContext.getCurrentInstance();
        EditProfileBean bean = (EditProfileBean) context.getExternalContext().getRequestMap().get(Constants.EDIT_PROFILE_BEAN);
        errors = Validator.validateEditProfileForm(bean);
        if (!errors.isEmpty()) {
            String s = errors.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(Constants.INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, s));
            return;
        }

        User updatedUser = Util.convertEditProfileBean(bean, user);
        if (isExistingEmail(updatedUser)) {
            String message = "User with that email already exists";
            errors.put("email", "User with that email already exists");
            LOGGER.info(Constants.INVALID_INPUT + message);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, message));
            return;
        }

        if (user.equals(updatedUser)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.NO_CHANGES_DETECTED, null));
        } else if (userService.update(updatedUser)) {
            user = updatedUser;
            LOGGER.info(Constants.PROFILE_UPDATED_SUCCESSFULLY + " User id = " + user.getId() );
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.PROFILE_UPDATED_SUCCESSFULLY, null));
        } else {
            LOGGER.error(Constants.ERROR_EDITING_USER + " User id = " + user.getId() );
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.ERROR_EDITING_USER, null));
        }
    }

    private boolean isExistingEmail(User updatedUser) {
        User dbUser = userService.findByEmail(updatedUser.getEmail());
        return dbUser != null && !dbUser.getId().equals(updatedUser.getId());
    }

    public void changePassword() {
        FacesContext context = FacesContext.getCurrentInstance();
        ChangePasswordBean bean = (ChangePasswordBean) context.getExternalContext().getRequestMap().get(Constants.CHANGE_PASSWORD_BEAN);
        errors = Validator.validateChangePasswordForm(bean);
        if (!errors.isEmpty()) {
            String s = errors.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(Constants.INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, s));
            return;
        }
        User updatedUser = Util.convertChangePasswordBean(bean, user);
        User dbUser = userService.findById(updatedUser.getId());
        if (user.getPassword().equals(updatedUser.getPassword())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.NO_CHANGES_DETECTED, null));
        } else if (dbUser.getPassword().equals(bean.getCurrentPassword())) {
            if (userService.update(updatedUser)) {
                user = updatedUser;
                LOGGER.info(Constants.PROFILE_UPDATED_SUCCESSFULLY + " User id = " + user.getId());
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.PASSWORD_UPDATED_SUCCESSFULLY, null));
                try {
                    EmailNotificator.sendPasswordChangedNotification(updatedUser);
                } catch (MessagingException e) {
                    LOGGER.error("Error email notification", e);
                }
            } else {
                LOGGER.error(Constants.ERROR_CHANGING_PASSWORD + " User id = " + user.getId() );

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.ERROR_CHANGING_PASSWORD, null));
            }
        } else {
            String message = "You entered wrong user password";
            errors.put("currentPassword", message);
            LOGGER.info(Constants.ERROR_CHANGING_PASSWORD + message + ". User id = " + user.getId());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.ERROR_CHANGING_PASSWORD, message));
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

    public void verifyLogin() {
        if (user==null) {
            redirect(Constants.LOGIN_PATH);
        }
    }
    public void verifyIsLoggedIn() {
        if (user!=null) {
            redirect(Constants.HOME_PATH);
        }
    }


}
