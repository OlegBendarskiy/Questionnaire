package oleh_bendarskyi.intership_project.questionnaire.utils;

import oleh_bendarskyi.intership_project.questionnaire.models.User;
import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailNotificator {
    private static final Logger LOGGER =
            Logger.getLogger(EmailNotificator.class);

    public static void sendSignUpNotification(User user) throws MessagingException {
        String subject = "LogoType.com";
        String body = "Hi, you have successfully created an account on the LogoType.com!"
                + "<br/>"
                + "password = " + user.getPassword();
        LOGGER.info("Subject: " + subject);
        LOGGER.info("Body: " + body);
        generateAndSendEmail(user.getEmail(), subject, body);
    }

    public static void sendPasswordChangedNotification(User user) throws MessagingException {
        String subject = "LogoType.com";
        String body = "Your password has been successfully changed on the LogoType.com!"
                + "<br/>"
                + "new password = " + user.getPassword();
        LOGGER.info("Subject: " + subject);
        LOGGER.info("Body: " + body);
        generateAndSendEmail(user.getEmail(), subject, body);
    }

    private static void generateAndSendEmail(String addressee, String subject, String body) throws MessagingException {
        Properties mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        MimeMessage generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(addressee));
        generateMailMessage.setSubject(subject);
        generateMailMessage.setContent(body, "text/html");
        Transport transport = getMailSession.getTransport("smtp");

        transport.connect("smtp.gmail.com", "qqqq7958747qq123456123456@gmail.com", "qwerf1234");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
