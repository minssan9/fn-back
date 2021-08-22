package com.core.common.util;

import com.core.common.dto.EmailDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailUtil {

    JavaMailSender javaMailSender;

    public void sendSimpleMessage(EmailDto emailDto)
        throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(new InternetAddress(emailDto.getFrom()));
        message.addRecipients(RecipientType.TO, emailDto.getReceiveList());
        message.setRecipients(RecipientType.BCC, emailDto.getCcList());
        message.setSubject(emailDto.getSubject());
        message.setText(emailDto.getText());

        javaMailSender.send(message);
    }

    public void sendMessageWithFile(EmailDto emailDto)
        throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(emailDto.getFrom());
        helper.setTo( emailDto.getReceiveList());
        helper.setCc( emailDto.getCcList());
        helper.setSubject(emailDto.getSubject());
        helper.setText(emailDto.getText());

        File attachFile = new File(emailDto.getPathToAttachment());
        FileSystemResource file= new FileSystemResource(attachFile);

        helper.addAttachment(attachFile.getName(), file);

        javaMailSender.send(message);
    }
}
