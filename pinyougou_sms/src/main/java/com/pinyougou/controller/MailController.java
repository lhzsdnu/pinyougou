package com.pinyougou.controller;

import com.pinyougou.model.Message;
import com.pinyougou.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MailController {

    @Autowired
    private MailService mailService;


    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public void sendMailMessage() {
        Message message = new Message();

        message.setCode("123456");

        mailService.sendMessageMail(message, "测试消息通知", "message.ftl");

        mailService.sendMessageMail(message, "测试消息通知", "message.ftl","2383376138@qq.com");
    }
}
