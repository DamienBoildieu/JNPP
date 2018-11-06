package jnpp.service.services;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailbox {

    private String username;
    private Properties properties;
    private Authenticator authenticator;
    
    public Mailbox(String host, String port, 
            final String username, final String password) {
        
        this.username = username;
        
        properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        
        authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
    }

    public void send(String address, String subject, String message)
            throws AddressException, MessagingException {
        
        Session session = Session.getInstance(properties, authenticator);
        
        Message msg = new MimeMessage(session);
        
        msg.setFrom(new InternetAddress(username));
        InternetAddress[] toAddresses = {new InternetAddress(address)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);
        
        Transport.send(msg);
    }

}
