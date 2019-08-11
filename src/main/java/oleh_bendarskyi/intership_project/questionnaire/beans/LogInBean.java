package oleh_bendarskyi.intership_project.questionnaire.beans;

import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;


@Data
@ManagedBean(name = "loginBean")
@RequestScoped
public class LogInBean implements Serializable {
    private String email;
    private String password;
}