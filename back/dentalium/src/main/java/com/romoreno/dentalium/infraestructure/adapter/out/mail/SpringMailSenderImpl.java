package com.romoreno.dentalium.infraestructure.adapter.out.mail;

import com.romoreno.dentalium.domain.port.out.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SpringMailSenderImpl implements MailSender {

    private final JavaMailSender javaMailSender;

    public SpringMailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendEmail(String from, String to, String subject, String text) {
        new Thread(() -> {
            try {
                final var simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setFrom(from);
                simpleMailMessage.setTo(to);
                simpleMailMessage.setSubject(subject);
                simpleMailMessage.setText(text);
                javaMailSender.send(simpleMailMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        return true;
    }
}
