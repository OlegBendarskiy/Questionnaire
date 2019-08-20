package oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands;

import oleh_bendarskyi.intership_project.questionnaire.beans.ChangePasswordBean;
import oleh_bendarskyi.intership_project.questionnaire.context.UserContext;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.Command;
import oleh_bendarskyi.intership_project.questionnaire.models.User;
import oleh_bendarskyi.intership_project.questionnaire.service.UserService;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;
import oleh_bendarskyi.intership_project.questionnaire.utils.EmailNotificator;
import oleh_bendarskyi.intership_project.questionnaire.utils.Util;
import oleh_bendarskyi.intership_project.questionnaire.utils.Validator;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import java.util.stream.Collectors;

public class ChangePasswordCommand extends Command {


    private UserService userService;
    private static final Logger LOGGER =
            Logger.getLogger(Command.class);

    private UserContext userContext;
    public ChangePasswordCommand(UserService userService, UserContext userContext) {
        this.userService = userService;
        this.userContext = userContext;
    }


    @Override
    public void execute() {
        FacesContext context = FacesContext.getCurrentInstance();
        ChangePasswordBean bean = (ChangePasswordBean) context.getExternalContext().getRequestMap().get(Constants.CHANGE_PASSWORD_BEAN);
        userContext.setErrors(Validator.validateChangePasswordForm(bean));
        if (!userContext.getErrors().isEmpty()) {
            String s = userContext.getErrors().entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(Constants.INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, s));
            return;
        }
        User updatedUser = Util.convertChangePasswordBean(bean, userContext.getUser());
        User dbUser = userService.findById(updatedUser.getId());
        if (userContext.getUser().getPassword().equals(updatedUser.getPassword())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.NO_CHANGES_DETECTED, null));
        } else if (dbUser.getPassword().equals(bean.getCurrentPassword())) {
            if (userService.update(updatedUser)) {
                userContext.setUser(updatedUser);
                LOGGER.info(Constants.PROFILE_UPDATED_SUCCESSFULLY + " User id = " + userContext.getUser().getId());
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.PASSWORD_UPDATED_SUCCESSFULLY, null));
                try {
                    EmailNotificator.sendPasswordChangedNotification(updatedUser);
                } catch (MessagingException e) {
                    LOGGER.error("Error email notification", e);
                }
            } else {
                LOGGER.error(Constants.ERROR_CHANGING_PASSWORD + " User id = " + userContext.getUser().getId() );

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.ERROR_CHANGING_PASSWORD, null));
            }
        } else {
            String message = "You entered wrong user password";
            userContext.getErrors().put("currentPassword", message);
            LOGGER.info(Constants.ERROR_CHANGING_PASSWORD + message + ". User id = " + userContext.getUser().getId());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.ERROR_CHANGING_PASSWORD, message));
        }
    }

}
