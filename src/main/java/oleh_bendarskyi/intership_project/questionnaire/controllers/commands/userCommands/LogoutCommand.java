package oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands;

import oleh_bendarskyi.intership_project.questionnaire.context.UserContext;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.Command;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;
import org.apache.log4j.Logger;

import javax.faces.context.FacesContext;

public class LogoutCommand extends Command {

    private static final Logger LOGGER =
            Logger.getLogger(Command.class);
    private UserContext userContext;

    public LogoutCommand(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    public void execute() {
        long id = userContext.getUser().getId();

        // invalidate the session
        LOGGER.debug("invalidating session for userContext with id: " + id);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext()
                .invalidateSession();
        LOGGER.info("logout successful for userContext with id: " + id);
        redirect(context, Constants.HOME_PATH);
    }
}
