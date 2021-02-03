package com.open.utils.email;

import com.sun.mail.util.MailSSLSocketFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * 测试邮件工具类
 * @author ：zc
 * @createTime ：2021/2/3 13:44
 */
public class EmailTest {




    @Test
    public void sendEmailTest() {
//#JavaMailSender 邮件发送的配置
//#mail.host=smtp.exmail.qq.com
//        mail.host=smtp.163.com
//        mail.username=15927294078@163.com
//        mail.password=prozhu123456
//        mail.smtp.auth=true
//        mail.properties.mail.smtp.starttls.enable=true
//        mail.properties.mail.smtp.starttls.required=true
//        mail.debug=true
    }


    //简单邮件测试
    @Test
    public void sendSimple(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.163.com");
        javaMailSender.setUsername("15927294078@163.com");
        javaMailSender.setPassword("prozhu123456");


        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("email测试");
        message.setText("邮件测试内容");
        message.setTo("496659989@qq.com");
        message.setFrom("15927294078@163.com");
        javaMailSender.send(message);
    }
    //复杂邮件测试
    public void sendComplicated() throws MessagingException {
        JavaMailSenderImpl javaMailSender = null;
        //创建一个复杂的消息邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //用MimeMessageHelper来包装MimeMessage
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setSubject("email测试");
        mimeMessageHelper.setText("邮件测试内容");
        mimeMessageHelper.setTo("fanqixxxx@vip.qq.com");
        mimeMessageHelper.setFrom("fanqixxxx@163.com");
//        mimeMessageHelper.addAttachment("meinv.jpg",new File("D:\\meinv.jpg"));
        javaMailSender.send(mimeMessage);

    }

}
