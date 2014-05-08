package checkit.plugin.check;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

public class Email implements CheckAgent {

    @Override
    public Object getCallRequiredParamsName() {
        return new Object[]{"url", "port", "username", "password", "smtp"};
    }

    @Override
    public Object getResultParamsName() {
        return new Object[]{"ok"};
    }

    @Override
    public Object run(Object[] params) {
        String url = params[0].toString();
        int port = Integer.valueOf(params[1].toString());
        String username = params[2].toString();
        String password = params[3].toString();
        boolean smtp = Boolean.parseBoolean(params[4].toString());

        Properties props = new Properties();
        if (smtp) {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
        } else {
            props.put("mail.store.protocol", "imap");
        }

        Session session = Session.getDefaultInstance(props);
        try {
            if (smtp) {
                Transport connection = session.getTransport("smtp");
                connection.connect(url, port, username, password);
            } else {
                Store connection = session.getStore("imaps");
                connection.connect(url, port, username, password);
            }
            return new Object[] {true};
        }catch (MessagingException e) {
            return new Object[] {false};
        }
    }

    @Override
    public Object isItOk(Object[] params) {
        return params[0];
    }
    
}
