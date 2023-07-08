package mailer;

import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {


    public static void main(String[] args) {
        // Sender's email address and password
        String senderEmail = "noreply@supportzebra.com";
        String userName = "AKIASKFBBHWC2AFKXROT";
        String senderPassword = "BNcZqYE+8bM6fTMfWwUpkokTz9+VRwcUQ+aqpiBlniNM";

        // Recipient's email address
        String recipientEmail = "gerzon.udang@talentamp.com";

        // Email subject and content
        String subject = "Test Email";
        String content = "This is a test email.";

        // Set up mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "email-smtp.us-west-2.amazonaws.com");
        properties.put("mail.smtp.port", "587");

        // Create a session with the mail server
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, senderPassword);
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setFrom(new InternetAddress(senderEmail));
            message.setSubject(subject);
            message.setText(content);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Failed to send email. Error: " + e.getMessage());
        }
    }
}
