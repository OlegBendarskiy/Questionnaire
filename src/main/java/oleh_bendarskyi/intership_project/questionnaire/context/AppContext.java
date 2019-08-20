package oleh_bendarskyi.intership_project.questionnaire.context;

import lombok.Data;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@Data
@ManagedBean(name = Constants.CONTEXT, eager = true)
@ApplicationScoped
public class AppContext {
    private String webappName;
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        webappName = extContext.getApplicationContextPath();
    }
}
