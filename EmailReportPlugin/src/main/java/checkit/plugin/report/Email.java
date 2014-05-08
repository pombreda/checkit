package checkit.plugin.report;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email implements Report {

    @Override
    public Object getOptionsJSON() {
        return "{"
             +    "\"inputs\": ["
             +      "{"
             +        "\"id\": \"email\""
             +        "\"type\": \"text\","
             +        "\"title\": \"Email\","
             +        "\"description\": \"e.g. my.email@example.com\""
             +      "}"
             +    "]"
             + "}";
    }

    @Override
    public Object getCallRequiredParamsName() {
        return new Object[]{"email"};
    }
    
    @Override
    public void reportDown(String testTitle, Object[] params) {
        //receives values for params from getCallRequiredParamsName(), in Object type. testTitle is sended always.
        String email = params[0].toString();
        sendEmail(email, "Monitor is DOWN: " + testTitle, "<p>Hi,<br />"
                + "<br />"
                + "The monitor " + testTitle + " is currently DOWN (Service Unavailable).<br />"
                + "<br />"
                + "For further details log in to your account at <a href=\"#\">myCheckIT</a>.<br />"
                + "<br />"
                + "Best regards,<br />"
                + "CheckIT!");
    }

    @Override
    public void reportUp(String testTitle, Object[] params) {
        //receives values for params from getCallRequiredParamsName(), in Object type. testTitle is sended always.
        String email = params[0].toString();
        sendEmail(email, "Monitor is UP: " + testTitle, "<p>Hi,<br />"
                + "<br />"
                + "The monitor " + testTitle + " is back UP (OK).<br />"
                + "<br />"
                + "For further details about antecedent failure log in to your account at <a href=\"#\">myCheckIT</a>.<br />"
                + "<br />"
                + "Best regards,<br />"
                + "CheckIT!");
    }

    @Override
    public void reportRegular(String testTitle, int numberOfDowns, long timeOfDowns, Object[] params) {
        //receives values for params from getCallRequiredParamsName(), in Object type. testTitle, numberOfDowns and timeOfDowns are sended always.
        String email = params[0].toString();
        sendEmail(email, "Weekly report: " + testTitle, "<p>Hi,<br />"
                + "<br />"
                + "In this week " + numberOfDowns + " failures occurred in a total time of " + timeOfDowns + " seconds.<br />"
                + "<br />"
                + "For further details log in to your account at <a href=\"#\">myCheckIT</a>.<br />"
                + "<br />"
                + "Best regards,<br />"
                + "CheckIT!");
    }

    private void sendEmail(String email, String subject, String message) {
        Properties props = new Properties();
        Properties settings = new Properties();
        
        try {
            InputStream input = Email.class.getClassLoader().getResourceAsStream("settings.properties");
            if (input == null) {
                return;
            }
            settings.load(input);
            final String host = settings.getProperty("mail.host");
            final String port = settings.getProperty("mail.port");
            final String username = settings.getProperty("mail.username");
            final String password = settings.getProperty("mail.password");

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message msg = new MimeMessage(session);
            msg.setSubject(subject);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email, false));
            msg.setContent(message, "text/html; charset=utf-8");
            msg.setSentDate(new Date());
            Transport.send(msg);       
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
