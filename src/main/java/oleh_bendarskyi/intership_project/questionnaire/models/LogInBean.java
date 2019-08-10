package oleh_bendarskyi.intership_project.questionnaire.models;

import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;


@Data
@ManagedBean(name = "loginBean", eager = true)
@RequestScoped
public class LogInBean implements Serializable {
    private String email;
    private String password;
}