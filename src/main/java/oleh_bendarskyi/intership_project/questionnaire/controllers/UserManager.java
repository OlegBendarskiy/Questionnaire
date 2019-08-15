package oleh_bendarskyi.intership_project.questionnaire.controllers;

import lombok.Data;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands.ChangePasswordCommand;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.Command;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands.EditProfileCommand;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands.LoginCommand;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands.LogoutCommand;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands.RegistrationCommand;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands.VerifyLoggedInCommand;
import oleh_bendarskyi.intership_project.questionnaire.controllers.commands.userCommands.VerifyLoginCommand;
import oleh_bendarskyi.intership_project.questionnaire.service.UserService;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@ManagedBean(name = Constants.USER_MANAGER)
@SessionScoped
public class UserManager implements Serializable {

    private static final Logger LOGGER =
            Logger.getLogger(UserManager.class);
    private UserContext userContext;

    @ManagedProperty(value = "#{"+ Constants.USER_SERVICE +"}")
    private UserService userService;
    private Map<String, Command> commands;


    @PostConstruct
    public void init() {
        userContext = new UserContext();
        commands = new HashMap<>();
        commands.put("performRegistration", new RegistrationCommand(userService, userContext));
        commands.put("performLogin", new LoginCommand(userService, userContext));
        commands.put("logout", new LogoutCommand(userContext));
        commands.put("editProfile", new EditProfileCommand(userService, userContext));
        commands.put("changePassword", new ChangePasswordCommand(userService, userContext));
        commands.put("verifyLogin", new VerifyLoginCommand(userContext));
        commands.put("verifyIsLoggedIn", new VerifyLoggedInCommand(userContext));
    }

    public void doCommand(String command){
        LOGGER.info("Start performing command: " + command);
        commands.get(command).execute();
    }

    public void clearErrors() {
        LOGGER.info("Cleaning errors");
        userContext.setErrors(null);
    }
}
