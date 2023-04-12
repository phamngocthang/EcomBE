package com.ecomerce.android.sendmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ecomerce.android.dto.EmailDTO;
import com.ecomerce.android.dto.ResponseDTO;

import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    /**
     * Method for sending simple e-mail message.
     * @param emailDTO - data to be send.
     */
    public Boolean sendSimpleMessage(EmailDTO emailDTO, ResponseDTO responseDTO)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailDTO.getRecipients().stream().collect(Collectors.joining(",")));
        mailMessage.setSubject(emailDTO.getSubject());
        mailMessage.setText(emailDTO.getBody());

        Boolean isSent = false;
        try
        {
            emailSender.send(mailMessage);
            isSent = true;
            responseDTO.setMessage("OTP Has Been Sent!!!");
        }
        catch (Exception e) {
        	System.out.print(e);
        	responseDTO.setMessage("Wrong When Sent OTP!!!");
        }
        return isSent;
    }


}
