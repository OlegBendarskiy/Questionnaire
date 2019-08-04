package oleh_bendarskyi.intership_project.questionnaire.models;

import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@Data
@ManagedBean(name = "user", eager = true)
@SessionScoped
public class User {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
}