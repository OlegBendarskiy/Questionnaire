package oleh_bendarskyi.intership_project.questionnaire.beans;

import lombok.Data;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;
import oleh_bendarskyi.intership_project.questionnaire.controllers.UserManager;
import oleh_bendarskyi.intership_project.questionnaire.models.User;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@Data
@ManagedBean(name= Constants.EDIT_PROFILE_BEAN)
@RequestScoped
public class EditProfileBean implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserManager userManager = (UserManager) context.getExternalContext().getSessionMap().get("user");
        User user = userManager.getUserContext().getUser();
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setEmail(user.getEmail());
        setPhoneNumber(user.getPhoneNumber());
    }
}
