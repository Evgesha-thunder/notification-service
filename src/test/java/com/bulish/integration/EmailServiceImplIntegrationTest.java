package com.bulish.integration;

import com.bulish.service.EmailService;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;

import javax.mail.internet.MimeMessage;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class EmailServiceImplIntegrationTest {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withPerMethodLifecycle(false);

    @TestConfiguration
    static class TestMailConfiguration {
        @Bean
        public JavaMailSender javaMailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("localhost");
            mailSender.setPort(greenMail.getSmtp().getPort());
            mailSender.setProtocol("smtp");

            Properties props = new Properties();
            props.put("mail.smtp.auth", "false");
            props.put("mail.smtp.connectiontimeout", "5000");
            props.put("mail.smtp.timeout", "5000");
            props.put("mail.smtp.writetimeout", "5000");

            mailSender.setJavaMailProperties(props);
            return mailSender;
        }
    }

    @BeforeEach
    void setUp() {
        greenMail.reset();
    }

    @Autowired
    private EmailService emailService;

     private final String SUBJECT = "User operation notification";

    @Test
    @DisplayName("send email when user is created - ok")
    void sendEmailCreatedOk() throws Exception {
        String to = "test_created@example.com";
        String MSG_CREATED = "Здравствуйте! Ваш аккаунт на сайте был успешно создан.";

        emailService.sendEmail(to, SUBJECT, MSG_CREATED);

        greenMail.waitForIncomingEmail(5000, 1);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        MimeMessage message = receivedMessages[0];

        assertAll("received message",
                () -> assertEquals(1, receivedMessages.length),
                () -> assertEquals(SUBJECT, message.getSubject()),
                () -> assertEquals(to, message.getAllRecipients()[0].toString()),
                () -> assertNotNull(message));
    }

    @Test
    @DisplayName("send email when user is deleted - ok")
    void sendEmailDeletedOk() throws Exception {
        String to = "test_delete@example.com";
        String MSG_DELETED = "Здравствуйте! Ваш аккаунт был удалён.";

        emailService.sendEmail(to, SUBJECT, MSG_DELETED);

        greenMail.waitForIncomingEmail(5000, 1);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        MimeMessage message = receivedMessages[0];

        assertAll("received message",
                () -> assertEquals(1, receivedMessages.length),
                () -> assertEquals(SUBJECT, message.getSubject()),
                () -> assertEquals(to, message.getAllRecipients()[0].toString()),
                () -> assertNotNull(message));
    }

    @Test
    @DisplayName("send email when invalid mail - throws error")
    void sendEmailWithInvalidMailThrowsError() throws Exception {
        String invalidTo = "@example.com";

        assertThrows(MailException.class, () -> {
            emailService.sendEmail(invalidTo, SUBJECT, "Some message");
        });
    }
}