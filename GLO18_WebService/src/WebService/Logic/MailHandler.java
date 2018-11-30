/*
Code taken from https://stackoverflow.com/a/15526256/5274680
*/
package WebService.Logic;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailHandler {
    public String sendMailAutogenerated(String ID, String mailTo, String name, String CustomerPassword) {

        final String username = "GroupTenSemesterProject@gmail.com";
        final String password = "\"#q=P'3-K]g[)7H*";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("GroupTenSemesterProject@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(mailTo));
            message.setSubject("Account info for Elena Banking");
            message.setText("Dear " + name + " here is your login information. It contains your unique ID which is used for login, as well as a random generated password.\n\n"
            + "ID: " + ID.substring(1) + "\n" + "Password: " + CustomerPassword +"Please change your password at the first posible obitunity"+ "\n\n Best regards from Group 10");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "Success";
    }
    
    public String sendNewPasswordAutogenerated(String ID, String mailTo, String name, String CustomerPassword) {

        final String username = "GroupTenSemesterProject@gmail.com";
        final String password = "\"#q=P'3-K]g[)7H*";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("GroupTenSemesterProject@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(mailTo));
            message.setSubject("Reset passwrod");
            message.setText("Dear " + name +"Your password has been reset, if this was not your doing please contact you Bank of Elena representative "+ " here is your login information. It contains your unique ID which is used for login, as well as a random generated password.\n\n"
            + "ID: " + ID.substring(1) + "\n" + "Password: " + CustomerPassword +"Please change your password at the first posible obitunity"+ "\n\n Best regards from Group 10");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "Success";
    }
}