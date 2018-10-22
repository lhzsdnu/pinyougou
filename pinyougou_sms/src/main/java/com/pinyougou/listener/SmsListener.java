package com.pinyougou.listener;

import com.pinyougou.model.Message;
import com.pinyougou.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息监听类
 */
@Component
public class SmsListener {

    @Autowired
    private MailService mailService;

    @JmsListener(destination = "smsDestination")
    public void sendSms(Map<String, String> map) {
        try {
            String code = map.get("mobile");
            System.out.println("验证码为：" + code);
            Message message = new Message();
            message.setCode(code);
            mailService.sendMessageMail(message, "测试消息通知", "message.ftl","2383376138@qq.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
