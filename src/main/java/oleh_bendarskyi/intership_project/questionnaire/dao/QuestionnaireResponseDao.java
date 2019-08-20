package oleh_bendarskyi.intership_project.questionnaire.dao;

import org.hibernate.Session;

import java.util.List;
import java.util.Map;

public interface QuestionnaireResponseDao {
    void create(Map<String, Object> questionnaireRecord, Session session);
    List<Map<String, Object>> getAll(List<String> fields, Session session);

    void createColumn(String columnName, Session session);
    void deleteColumn(String columnName, Session session);
    void updateColumn(String oldColumnName, String newColumnName, Session session);
}
