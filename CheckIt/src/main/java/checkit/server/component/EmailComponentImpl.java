/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The EmailComponent implementation
 * All services related to emailing
 */

package checkit.server.component;

import checkit.server.domain.User;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EmailComponentImpl implements EmailComponent {
    @Autowired
    private JavaMailSender mailSender;
    
    /**
     * Send plain text email
     *
     * @param email Recipients email address
     * @param subject Subject of email
     * @param message Emails body.
     */
    private void sendEmail(String email, String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(message);
        mailSender.send(msg);
    }
    
    /**
     * Send HTML email
     *
     * @param email Recipients email address
     * @param subject Subject of email
     * @param message Emails body.
     */
    private void sendHtmlEmail(String email, String subject, String message) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(msg);
        } catch (MessagingException ex) {
        }

    }
    
    /**
     * Check if string is valid email
     *
     * @param email Tested string
     * 
     * @return true if string is email, false otherwise.
     */
    @Override
    public boolean isEmailValid(String email) {
	final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Generate hash for activation or changing link
     *
     * @param user User for whom is hash generated.
     * 
     * @return Final unique hash.
     */
    @Override
    public String getActivationCode(User user) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-1");
        String hash = encoder.encodePassword(user.getUsername(), dateFormat.format(date));
        return hash;
    }

    /**
     * Create and send email for activation
     *
     * @param user User to whom is email sended.
     * @param hash Already generated unique hash.
     */
    @Override
    @Async
    public void sendActivationLink(User user, String hash) {
        String message = 
            "Dear " + user.getUsername() + ",<br /><br />" +
            "Your registration is almost done, only last thing missing. This is your activation link:<br />" +
            "<a href=\"http://localhost:8080/CheckIt/confirm?activation=" + hash + "\" target=\"_blank\">http://localhost:8080/CheckIt/confirm?activation=" + hash + "</a><br />" +
            "Just click the link and your registration will be done. If you do not request account on CheckIT website, please, ignore this message.<br /><br />" +
            "CheckIT<br />" +
            "<a href=\"http://localhost:8080/CheckIt/\" target=\"_blank\">http://localhost:8080/CheckIt/</a>";
        String subject = "CheckIT - activation link";
        
        sendHtmlEmail(user.getEmail(), subject, message);
    }

    /**
     * Create and send email for changing
     *
     * @param user User to whom is email sended.
     * @param hash Already generated unique hash.
     */
    @Override
    @Async
    public void sendUpdateLink(User user, String hash) {
        String message = 
            "Dear " + user.getUsername() + ",<br /><br />" +
            "Please, confirm by clicking on the link below this is your email address:<br />" +
            "<a href=\"http://localhost:8080/CheckIt/confirm?change=" + hash + "\" target=\"_blank\">http://localhost:8080/CheckIt/confirm?change=" + hash + "</a><br />" +
            "Until you confirm this request, your old email address is still active.<br /><br />" +
            "CheckIT<br />" +
            "<a href=\"http://localhost:8080/CheckIt/\" target=\"_blank\">http://localhost:8080/CheckIt/</a>";
        String subject = "CheckIT - activation link";
        
        sendHtmlEmail(user.getEmail(), subject, message);
    }
    
}
