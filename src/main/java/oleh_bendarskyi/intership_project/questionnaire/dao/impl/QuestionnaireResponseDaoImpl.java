package oleh_bendarskyi.intership_project.questionnaire.dao.impl;

import oleh_bendarskyi.intership_project.questionnaire.dao.QuestionnaireResponseDao;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class QuestionnaireResponseDaoImpl implements QuestionnaireResponseDao {

    private static final Logger LOGGER =
            Logger.getLogger(QuestionnaireResponseDaoImpl.class);

    @Override
    public void create(Map<String, Object> questionnaireRecord, Session session) {
        List<String> keys = questionnaireRecord.keySet().stream()
                .filter(x -> questionnaireRecord.get(x) != null)
                .collect(Collectors.toList());
        String query = "INSERT INTO response("
                + keys.stream()
                .map(x -> "\"" + x + "\"")
                .collect(Collectors.joining(","))
                + ")VALUES("
                + keys.stream()
                //          .map(x -> "'" + questionnaireRecord.get(x).toString() + "'")
                .map(x -> "?")
                .collect(Collectors.joining(","))
                + ")";
        LOGGER.info("query: " + query);
        Query create = session.createNativeQuery(query);
        for (int i = 0; i < keys.size(); i++) {
            create.setParameter(i + 1, questionnaireRecord.get(keys.get(i)).toString());
        }
        create.executeUpdate();
    }

    @Override
    public List<Map<String, Object>> getAll(List<String> fields, Session session) {
        List<Map<String, Object>> questionnaireRecords = new ArrayList<>();
        String query = "SELECT " + fields.stream()
                .map(x -> "\"" + x + "\"")
                .collect(Collectors.joining(",")) + " FROM response";
        LOGGER.info("query: " + query);
        List<Object[]> responses = session.createSQLQuery(query).list();
        for (Object[] row : responses) {
            Map<String, Object> record = new HashMap<>();
            for (int i = 0; i < row.length; i++) {
                String key = (fields.get(i));
                Object value = row[i];
                record.put(key, value);
            }
            questionnaireRecords.add(record);
        }
        return questionnaireRecords;
    }

    @Override
    public void createColumn(String columnName, Session session) {
        String query = "ALTER TABLE response ADD COLUMN " + columnName +" character varying NOT NULL DEFAULT 'N/A'";
        LOGGER.info("query: " + query);
        Query createColumn = session.createNativeQuery(query);
        createColumn.executeUpdate();
    }

    @Override
    public void deleteColumn(String columnName, Session session) {
        String query = "ALTER TABLE response DROP COLUMN " + columnName;
        LOGGER.info("query: " + query);
        Query createColumn = session.createNativeQuery(query);
        createColumn.executeUpdate();
    }

    @Override
    public void updateColumn(String oldColumnName, String newColumnName, Session session) {
        String query = "ALTER TABLE response RENAME COLUMN " + oldColumnName + " TO " + newColumnName;
        LOGGER.info("query: " + query);
        Query createColumn = session.createNativeQuery(query);
        createColumn.executeUpdate();
    }
}
