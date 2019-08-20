package oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands;

import oleh_bendarskyi.intership_project.questionnaire.beans.EditProfileBean;
import oleh_bendarskyi.intership_project.questionnaire.context.UserContext;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.Command;
import oleh_bendarskyi.intership_project.questionnaire.models.User;
import oleh_bendarskyi.intership_project.questionnaire.service.UserService;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;
import oleh_bendarskyi.intership_project.questionnaire.utils.Util;
import oleh_bendarskyi.intership_project.questionnaire.utils.Validator;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.stream.Collectors;

public class EditProfileCommand extends Command {

    private UserContext userContext;
    private UserService userService;
    private static final Logger LOGGER =
            Logger.getLogger(Command.class);

    public EditProfileCommand(UserService userService, UserContext userContext) {
        this.userService = userService;
        this.userContext = userContext;
    }


    @Override
    public void execute() {
        FacesContext context = FacesContext.getCurrentInstance();
        EditProfileBean bean = (EditProfileBean) context.getExternalContext().getRequestMap().get(Constants.EDIT_PROFILE_BEAN);
        userContext.setErrors(Validator.validateEditProfileForm(bean));
        if (!userContext.getErrors().isEmpty()) {
            String s = userContext.getErrors().entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue() + "; ").collect(Collectors.joining());
            LOGGER.info(Constants.INVALID_INPUT + s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, s));
            return;
        }

        User updatedUser = Util.convertEditProfileBean(bean, userContext.getUser());
        if (isExistingEmail(updatedUser)) {
            String message = "User with that email already exists";
            userContext.getErrors().put("email", "User with that email already exists");
            LOGGER.info(Constants.INVALID_INPUT + message);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.INVALID_INPUT, message));
            return;
        }

        if (userContext.getUser().equals(updatedUser)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.NO_CHANGES_DETECTED, null));
        } else if (userService.update(updatedUser)) {
            userContext.setUser(updatedUser);
            LOGGER.info(Constants.PROFILE_UPDATED_SUCCESSFULLY + " User id = " + userContext.getUser().getId() );
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.PROFILE_UPDATED_SUCCESSFULLY, null));
        } else {
            LOGGER.error(Constants.ERROR_EDITING_USER + " User id = " + userContext.getUser().getId() );
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.ERROR_EDITING_USER, null));
        }
    }

    private boolean isExistingEmail(User updatedUser) {
        User dbUser = userService.findByEmail(updatedUser.getEmail());
        return dbUser != null && !dbUser.getId().equals(updatedUser.getId());
    }

}
