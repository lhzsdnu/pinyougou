package com.pinyougou.service;

public interface MailService {
    /**
     * 测试，发件人和收件人是同一个人
     * @param params
     * @param title
     * @param templateName
     */
    void sendMessageMail(Object params, String title, String templateName);

    /**
     * 实际应用，需要传入收件人是谁
     * @param params
     * @param title
     * @param templateName
     * @param toUser
     */
    void sendMessageMail(Object params, String title, String templateName,String toUser);
}
