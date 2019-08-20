package oleh_bendarskyi.intership_project.questionnaire.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questionnaire_field", schema = "public")
public class QuestionnaireField implements Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String label;
    private String type;
    private String options = StringUtils.EMPTY;
    private boolean required;
    @Column(name="is_active")
    private boolean active;
}