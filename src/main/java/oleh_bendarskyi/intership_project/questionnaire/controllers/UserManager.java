package oleh_bendarskyi.intership_project.questionnaire.controllers;

import lombok.Data;
import oleh_bendarskyi.intership_project.questionnaire.Utils.Util;
import oleh_bendarskyi.intership_project.questionnaire.Utils.Validator;
import oleh_bendarskyi.intership_project.questionnaire.models.LogInBean;
import oleh_bendarskyi.intership_project.questionnaire.models.SignUpBean;
import oleh_bendarskyi.intership_project.questionnaire.models.User;
import oleh_bendarskyi.intership_project.questionnaire.service.UserService;
import oleh_bendarskyi.intership_project.questionnaire.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
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
    @ViewScoped
    private Map<String, String> errors;
    private User user;
    private UserService userService;

    public UserManager() {
        userService = new UserServiceImpl();
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public String getErrorMessage(String name) {
        if (errors == null) {
            return null;
        }
        return errors.get(name);
    }

    public boolean hasError(String name) {
        if (errors == null) {
            return false;
        }
        return errors.get(name) != null;
    }

    public void performLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> request = context.getExternalContext().getRequestMap();
        LogInBean bean = (LogInBean) request.get("loginBean");
        errors = null;
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
        Map<String, Object> request = context.getExternalContext().getRequestMap();
        SignUpBean bean = (SignUpBean) request.get("signUpBean");
        System.out.println(bean.getPhoneNumber() + "|||||||||||||||||||||||||||||||||||||||");
        errors = Validator.validateSignUpForm(bean);
        if (!errors.isEmpty()) {
            String s = errors.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_INPUT, s));
            return;
        }
        User newUser = Util.convertSignUpBean(bean);
        user = userService.create(newUser);
        redirect("/webapp/");
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
