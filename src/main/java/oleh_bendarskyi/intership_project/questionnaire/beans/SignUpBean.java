package oleh_bendarskyi.intership_project.questionnaire.beans;

import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;


@Data
@ManagedBean(name = "signUpBean")
@RequestScoped
public class SignUpBean implements Serializable {
    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}