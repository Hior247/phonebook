package com.msg.phonebook.service.impl;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

	@Service
	public class Demo_Sm {
		
		private String mailServerHost="smtp.qq.com";
		private String mailServerPort="25";
		private boolean validate=true;
		private String password="family1314";
		private String mailAdress="1062503928@qq.com";
		private String subject="找回密码";
		
		public void send(String s,String mycontent) throws UnsupportedEncodingException {
			// 设置邮件服务器信息 
			MailSenderInfo mailInfo = new MailSenderInfo();  
			mailInfo.setMailServerHost(mailServerHost);  
			mailInfo.setMailServerPort(mailServerPort);  
			mailInfo.setValidate(validate);  
			// 邮箱用户名  
			mailInfo.setUserName(mailAdress);  
			// 邮箱密码  
			mailInfo.setPassword(password);  
			// 发件人邮箱  
			mailInfo.setFromAddress(mailAdress);  
			// 收件人邮箱  
			mailInfo.setToAddress(s);  
			// 邮件标题  
			mailInfo.setSubject(subject);  
			// 邮件内容  
			StringBuffer buffer = new StringBuffer(); 
			buffer.append(mycontent); 
			mailInfo.setContent(buffer.toString());   
			// 发送邮件  
			SimpleMailSender sms = new SimpleMailSender();  
			// 发送文体格式 
			sms.sendTextMail(mailInfo);  
			// 发送html格式  
			SimpleMailSender.sendHtmlMail(mailInfo);  
			System.out.println("邮件发送完毕"); 
		}

	}

