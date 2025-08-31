package com.bulish.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "notifications")
public class NotificationProperties {
    private Messages messages = new Messages();

    @Data
    public static class Messages {
        private String create = "Здравствуйте! Ваш аккаунт на сайте был успешно создан.";
        private String delete = "Здравствуйте! Ваш аккаунт был удалён.";
    }
}
