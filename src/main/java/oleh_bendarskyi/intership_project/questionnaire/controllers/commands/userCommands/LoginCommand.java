package oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands;

import oleh_bendarskyi.intership_project.questionnaire.beans.LogInBean;
import oleh_bendarskyi.intership_project.questionnaire.context.UserContext;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.Command;
import oleh_bendarskyi.intership_project.questionnaire.service.UserService;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;
import oleh_bendarskyi.intership_project.questionnaire.utils.Validator;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.stream.Collectors;

public class LoginCommand extends Command {


    private UserService userService;
    private static final Logger LOGGER =
            Logger.getLogger(Command.class);

    private UserContext userContext;

    public LoginCommand(UserService userService, UserContext context) {
        this.userService = userService;
        this.userContext = context;
    }


    @Override
    public void execute() {
        FacesContext context = FacesContext.getCurrentInstance();
        LogInBean bean = (LogInBean) context.getExternalContext().getRequestMap().get(Constants.LOGIN_BEAN);
        userContext.setErrors(Validator.validateLogInFormBean(bean));
        if (!userContext.getErrors().isEmpty()) {
            String s = userContext.getErrors().entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(Constants.INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, s));
            return;
        }
        userContext.setUser(userService.findByEmail(bean.getEmail()));
        if (userContext.getUser() != null && userContext.getUser().getPassword().equals(bean.getPassword())) {
            redirect(context, Constants.HOME_PATH);
        } else {
            String s = "USER IS NOT FOUND!";
            LOGGER.info(s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, s, null));
        }
    }
}
