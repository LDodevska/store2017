package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.jpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by Viktor on 09-Jun-17.
 */
@Service
public class NotificationServiceImpl {
    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationServiceImpl(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendMessage(String email,String subject,String text) throws MailException{
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("rckskopje@gmail.com");
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);

    }

    public void receiveMessage(String name, String email, String comment){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("rckskopje@gmail.com");
        mail.setFrom(email);
        mail.setSubject(name + " comment");
        mail.setText(comment);

        javaMailSender.send(mail);
    }
}
