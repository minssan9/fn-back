package com.core.config.properties;

import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
@Configuration
public class CoreProperties {
    public static DateTimeFormatter DATE_STRING_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static DateTimeFormatter TIME_STRING_FORMAT = DateTimeFormatter.ofPattern("HHmm");

    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");


    @Value("${ecos-api-key}")
    private String ECOS_API_KEY;
    @Value("${ecos-api-url}")
    private String ECOS_API_URL;

}
