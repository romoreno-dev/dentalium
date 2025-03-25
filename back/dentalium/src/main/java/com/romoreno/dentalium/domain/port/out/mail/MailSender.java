package com.romoreno.dentalium.domain.port.out.mail;

public interface MailSender {

    boolean sendEmail(String from, String to, String subject, String text);

}
