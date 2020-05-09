package cn.crap.service.tool;

import cn.crap.dto.MailBean;
import cn.crap.enu.SettingEnum;
import cn.crap.service.IEmailService;
import cn.crap.beans.Config;
import cn.crap.utils.Aes;
import cn.crap.utils.IConst;
import cn.crap.utils.ISetting;
import cn.crap.utils.Tools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService implements IEmailService {
	private Log logger = LogFactory.getLog(IEmailService.class);// 日志类

	@Autowired
	private JavaMailSenderImpl mailSenderService;
	@Autowired
	private SettingCache settingCache;
	@Autowired
	private StringCache stringCache;

	@Override
	public void sendMail(MailBean mailBean) throws UnsupportedEncodingException, MessagingException{
		String fromName = settingCache.get(ISetting.S_TITLE).getValue();
		MimeMessage mimeMessage = mailSenderService.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		messageHelper.setFrom(mailSenderService.getUsername(), fromName); 
		messageHelper.setSubject(mailBean.getSubject());  
		messageHelper.setTo(mailBean.getToEmail());  
		messageHelper.setText(mailBean.getContext(), true);// html: true
		mailSenderService.send(mimeMessage);
	}
	
	@Override
	public boolean sendMail(String subject, String toEmail, String context) {
		try {
			String fromName = settingCache.get(ISetting.S_TITLE).getValue();
			MimeMessage mimeMessage = mailSenderService.createMimeMessage();
			MimeMessageHelper messageHelper;
			messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			messageHelper.setFrom(mailSenderService.getUsername(), fromName); 
			messageHelper.setSubject(subject);  
			messageHelper.setTo(toEmail);  
			messageHelper.setText(context, true);// html: true 
			mailSenderService.send(mimeMessage);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("邮件发送失败：subject="+subject+"，toEmail="+toEmail, e);
			return false;
		}
	}
	
	@Override
	public void sendRegisterEmail(String eamil, String id) throws UnsupportedEncodingException, MessagingException{
		String code =  Aes.encrypt(id);
		String domain = Tools.getUrlPath() + "/user/validateEmail.do?i=" + code;
		MailBean mailBean = new MailBean();
		mailBean.setContext( getMtml(eamil, "注册邮箱验证", "点击验证邮箱：<a href=\""+domain+"\">"+domain+"</a>"));
		mailBean.setToEmail(eamil);
		mailBean.setSubject("注册邮箱验证-开源API接口管理系统");
		sendMail(mailBean);
        logger.warn(mailBean.getContext());
		stringCache.add(code, IConst.REGISTER);
	}
	
	@Override
	public void sendFindPwdEmail(String eamil) throws UnsupportedEncodingException, MessagingException{
		MailBean mailBean = new MailBean();
		String code = Tools.getChar(6);
		mailBean.setContext( getMtml(eamil, "找回密码", "邮件验证码为："+code));
		mailBean.setToEmail(eamil);
		mailBean.setSubject("找回密码-开源API接口管理系统");
		sendMail(mailBean);
		stringCache.add(IConst.CACHE_FINDPWD+ eamil, code);
	}
	
	private String getMtml(String eamil, String title, String content){
		StringBuffer sb = new StringBuffer();
		sb.append("<div style=\"position:relative;width:400px;margin:0 auto; background:#f7f7f7;color:#999999; font-size:14px;line-height:36px;\">");
		sb.append("<div style=\"height:60px; border-bottom:2px solid #f82c1d;padding:10px;\" >");
		sb.append("<div style=\"float:left;margin-left:10px; line-height:60px;font-size:18px;font-weight:bold;color:#555;width:360px;height:60px;overflow:hidden;text-align:left;\">");
		sb.append( title );
		sb.append("</div></div><div style=\"padding:20px;min-height:260px;white-space: pre-wrap;word-wrap: break-word;\">");
		sb.append(content);
		sb.append("</div><div style=\"padding:20px;text-align:right;margin-top:30px;\">");
		sb.append("<a style=\"color:#f82c1d;\" href=\"http://api.crap.cn?sj="
				+ System.currentTimeMillis() + "\">本网站由CrapApi提供技术与支持</a>");
		sb.append("<br></div></div>");
		return sb.toString();
	}
}
