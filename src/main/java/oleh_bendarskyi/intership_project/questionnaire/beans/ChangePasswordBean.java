package oleh_bendarskyi.intership_project.questionnaire.beans;

import lombok.Data;
import oleh_bendarskyi.intership_project.questionnaire.utils.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@Data
@ManagedBean(name= Constants.CHANGE_PASSWORD_BEAN)
@RequestScoped
public class ChangePasswordBean implements Serializable {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}

