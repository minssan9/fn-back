package com.core.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

//@ConfigurationProperties("common")
@Configuration
@Setter
@Getter
public class StaticProperties {
    public static DateTimeFormatter DATE_STRING_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static DateTimeFormatter TIME_STRING_FORMAT = DateTimeFormatter.ofPattern("HHmm");

    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static DateTimeFormatter DATE_FORMAT2 = DateTimeFormatter.ofPattern("yyyy-MM-dd a hh:mm:ss");

    public static long BASE_ORGANIZATION_ID= 87L;

    //    properties class extract 필요
//    @Value("${email.sender:holdings.it@halla.com}")
//    public static String SENDER_EMAIL;
    @Value("${common.email.sender}")
    private String senderEmail;

    @Value("${ecos-api-key}")
    private String ECOS_API_KEY;
    @Value("${ecos-api-url}")
    private String ECOS_API_URL;
}
