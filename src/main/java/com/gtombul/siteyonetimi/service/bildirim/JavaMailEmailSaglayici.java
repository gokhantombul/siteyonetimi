package com.gtombul.siteyonetimi.service.bildirim;

import com.gtombul.siteyonetimi.service.bildirim.saglayici.EmailSaglayici;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JavaMailEmailSaglayici implements EmailSaglayici {

    //private final JavaMailSender mailSender;

    @Override
    public String gonder(String eposta, String konu, String icerik, boolean html) throws MessagingException {
        /*MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");
        helper.setTo(eposta);
        helper.setSubject(konu);
        helper.setText(icerik, html);
        mailSender.send(msg);
        log.info("Email gönderildi -> {}", eposta);*/
        return "OK";
    }

}
