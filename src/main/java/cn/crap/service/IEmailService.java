package cn.crap.service;

import cn.crap.dto.MailBean;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IEmailService {

    void sendMail(MailBean mailBean) throws UnsupportedEncodingException, MessagingException;

    void sendRegisterEmail(String eamil, String id) throws UnsupportedEncodingException, MessagingException;

    void sendFindPwdEmail(String eamil) throws UnsupportedEncodingException, MessagingException;

    boolean sendMail(String subject, String toEmail, String context);

}