package oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands;

import oleh_bendarskyi.intership_project.questionnaire.beans.SignUpBean;
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

public class RegistrationCommand extends Command {


    private UserService userService;
    private static final Logger LOGGER =
            Logger.getLogger(Command.class);

    private UserContext userContext;
    public RegistrationCommand(UserService userService, UserContext userContext) {
        this.userService = userService;
        this.userContext = userContext;
    }


    @Override
    public void execute() {
            FacesContext context = FacesContext.getCurrentInstance();
            SignUpBean bean = (SignUpBean) context.getExternalContext().getRequestMap().get(Constants.SIGN_UP_BEAN);
            userContext.setErrors(Validator.validateSignUpForm(bean));
            if (!userContext.getErrors().isEmpty()) {
                String s = userContext.getErrors().entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
                LOGGER.info(Constants.INVALID_INPUT + s);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, s));
                return;
            }
            User newUser = Util.convertSignUpBean(bean);
            if (isExistingEmail(newUser)) {
                String message = "User with that email already exists";
                userContext.getErrors().put("email", "User with that email already exists");
                LOGGER.info(Constants.INVALID_INPUT + message);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, message));
                return;
            }
            userContext.setUser(userService.create(newUser));
            try {
                EmailNotificator.sendSignUpNotification(newUser);
            } catch (MessagingException e) {
                LOGGER.error("Error email notification", e);
            }
            redirect(context, Constants.HOME_PATH);
    }

    private boolean isExistingEmail(User updatedUser) {
        User dbUser = userService.findByEmail(updatedUser.getEmail());
        return dbUser != null && !dbUser.getId().equals(updatedUser.getId());
    }
}
