package com.core.common.dto;

import lombok.Getter;
import lombok.Setter;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;

@Getter@Setter
public class EmailDto {


    String from;
    List<String> receiveList;
    List<String> ccList;
    String subject;
    String text;
    String pathToAttachment;

    public EmailDto(String from, List<String> receiveList, List<String> ccList, String subject,
                    String text, String pathToAttachment) {
        this.from = from;
        this.receiveList =  receiveList;
        this.ccList = ccList;
        this.subject = subject;
        this.text = text;
        this.pathToAttachment = pathToAttachment;
    }

    public InternetAddress[] getReceiveList() throws AddressException {
        InternetAddress[] recipients = new InternetAddress[this.receiveList.size()];

        for (int i = 0; i < this.receiveList.size(); i++) {
            recipients[i] = new InternetAddress(receiveList.get(i));
        }
        return recipients;
    }

    public InternetAddress[] getCcList() throws AddressException {
        InternetAddress[] ccs = new InternetAddress[this.receiveList.size()];

        for (int i = 0; i < this.receiveList.size(); i++) {
            ccs[i] = new InternetAddress(receiveList.get(i));
        }
        return ccs;
    }
}
