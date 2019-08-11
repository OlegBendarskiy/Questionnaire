package oleh_bendarskyi.intership_project.questionnaire.filters;


import oleh_bendarskyi.intership_project.questionnaire.models.User;
import org.apache.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

public class MyFaceListener implements PhaseListener {

    private static final Logger LOGGER =
            Logger.getLogger(MyFaceListener.class);

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {

    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {

    }

    @Override
    public PhaseId getPhaseId() {
        return null;
    }
}