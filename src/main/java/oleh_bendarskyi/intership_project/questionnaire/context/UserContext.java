package oleh_bendarskyi.intership_project.questionnaire.context;

import lombok.Data;
import oleh_bendarskyi.intership_project.questionnaire.models.User;

import java.io.Serializable;
import java.util.Map;

@Data
public class UserContext implements Serializable {
    private Map<String, String> errors;
    private User user;
}
