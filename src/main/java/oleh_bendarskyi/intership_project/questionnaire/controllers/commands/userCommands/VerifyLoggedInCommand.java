package oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands;

import oleh_bendarskyi.intership_project.questionnaire.context.UserContext;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.Command;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;

import javax.faces.context.FacesContext;

public class VerifyLoggedInCommand extends Command {
    private UserContext userContext;

    public VerifyLoggedInCommand(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    public void execute() {
        if (userContext.getUser() != null) {
            redirect(FacesContext.getCurrentInstance(), Constants.HOME_PATH);
        }
    }

}
