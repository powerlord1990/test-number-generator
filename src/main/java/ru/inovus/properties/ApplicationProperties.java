package ru.inovus.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("application")
public class ApplicationProperties {

    private String[] letters;
    private String constant;
    private int maxDigits;
}
