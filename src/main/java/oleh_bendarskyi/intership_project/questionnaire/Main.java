package oleh_bendarskyi.intership_project.questionnaire;

import com.fasterxml.jackson.databind.ObjectMapper;
import oleh_bendarskyi.intership_project.questionnaire.utils.HibernateUtil;
import oleh_bendarskyi.intership_project.questionnaire.models.QuestionnaireResponse;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
      /*  QuestionnaireField questionnaireField = new QuestionnaireField((long) 4, "QQQ", "single line text", "", true, true);
        try {
            SessionFactory factory = HibernateUtil.getSessionFactory();
            Transaction tx = null;
            Long id;

            try (Session session = factory.openSession()) {
                tx = session.beginTransaction();
                id = (Long) session.save(questionnaireField);
                questionnaireField.setId(id);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
            }
        } finally {
            HibernateUtil.shutdown();
        }*/

        List<Map<String, String>> list = getAll();
        for (Map<String, String> a : list) {
            for (String s : a.keySet()) {
                System.out.println("(" + s + "|" + a.get(s) + ")");
            }
        }
    }

    public static List<Map<String, String>> getAll() {
        List<Map<String, String>> questionnaireRecords = null;
        Transaction tx = null;
        try {
            SessionFactory factory = HibernateUtil.getSessionFactory();
            try (Session session = factory.openSession()) {
                tx = session.beginTransaction();
                List<QuestionnaireResponse> questionnaireResponses = session.createQuery("from QuestionnaireResponse", QuestionnaireResponse.class).list();
                questionnaireResponses.forEach(System.out::println);
                if (!questionnaireResponses.isEmpty()) {
                    questionnaireRecords = formMapsList(questionnaireResponses);
                }
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
            }
        }finally {
            HibernateUtil.shutdown();
        }

        return questionnaireRecords;
    }

    private static List<Map<String, String>> formMapsList(List<QuestionnaireResponse> questionnaireResponses) {
        List<Map<String, String>> questionnaireRecords = new ArrayList<>();
        System.out.println(11);
        JSONParser jsonParser = new JSONParser();
        System.out.println(22);
        for (QuestionnaireResponse response : questionnaireResponses) {
            System.out.println(33);
            try (FileReader reader = new FileReader("..\\Responses" + "\\" + response.getFileName())) {
                System.out.println(1);
                Object obj = jsonParser.parse(reader);
                System.out.println(2);
                JSONObject record = (JSONObject) obj;
                System.out.println(record.toString() + "++++++++++++++++");
                questionnaireRecords.add(new ObjectMapper().readValue(record.toString(), HashMap.class));
            } catch (IOException | ParseException e) {
                System.out.println("error");
            }
        }
        return questionnaireRecords;
    }

}
