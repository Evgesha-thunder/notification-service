package com.bulish.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String text) {
        try {
            log.debug("sendEmail to {}, subject {}, text {}", to, subject, text);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(text);

            mailSender.send(mailMessage);
            log.info("Email sent successfully to: {}", to);
        } catch (MailException ex) {
            log.error("Failed to send email to: {}", to, ex);
            throw ex;
        }
    }
}
