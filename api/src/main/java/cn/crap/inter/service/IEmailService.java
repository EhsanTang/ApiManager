package cn.crap.inter.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import cn.crap.dto.MailBean;

public interface IEmailService {

	void sendMail(MailBean mailBean) throws UnsupportedEncodingException, MessagingException;

	void sendRegisterEmail(String eamil, String id) throws UnsupportedEncodingException, MessagingException;

	void sendFindPwdEmail(String eamil) throws UnsupportedEncodingException, MessagingException;

}