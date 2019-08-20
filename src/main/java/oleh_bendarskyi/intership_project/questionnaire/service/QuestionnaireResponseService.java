package oleh_bendarskyi.intership_project.questionnaire.service;

import java.util.List;
import java.util.Map;

public interface QuestionnaireResponseService {
    boolean create(Map<String, Object> questionnaireRecord);
    List<Map<String, Object>> getAll();
}
