package com.open.utils.email;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮件工具类
 * @author ：zc
 * @createTime ：2021/2/3 14:05
 */
@Slf4j
public class EmailManage {


    public static  boolean sendSimpleEmail(String title, String content, String receiver){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.163.com");
        javaMailSender.setUsername("15927294078@163.com");
        javaMailSender.setPassword("prozhu123456");


        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(title);
        message.setText(content);
        message.setTo(receiver);
        message.setFrom("15927294078@163.com");
        javaMailSender.send(message);
        log.info("邮件发送成功");
        return true;
    }

    @Test
    public void test() {
        sendSimpleEmail("title", "content", "496659989@qq.com");
    }
}
