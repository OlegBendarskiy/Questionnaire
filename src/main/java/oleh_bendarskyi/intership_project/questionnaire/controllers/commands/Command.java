package oleh_bendarskyi.intership_project.questionnaire.controllers.commands;

import org.apache.log4j.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

public abstract class Command {

    private static final Logger LOGGER =
            Logger.getLogger(Command.class);

    public abstract void execute();

    protected void redirect(FacesContext context, String path) {
        ExternalContext extContext = context.getExternalContext();
        String webappName = extContext.getApplicationContextPath();
        try {
            extContext.redirect(webappName + path);
        } catch (IOException e) {
            LOGGER.error("Cannot redirect to: " + path);
        }
    }
}