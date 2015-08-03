package com.msg.phonebook.test;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.msg.phonebook.model.PF;
import com.msg.phonebook.service.PhonebookService;
import com.msg.phonebook.service.impl.Demo_Mt;
import com.msg.phonebook.service.impl.MailSenderInfo;
import com.msg.phonebook.service.impl.SimpleMailSender;

public class ServiceImplTest {
	@	Autowired
	private PhonebookService phonebookService;
	private Demo_Mt MtService;
	@Before
    public void before(){                                                                   
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml", 
                "classpath:conf/spring-mybatis.xml"});
       phonebookService = (PhonebookService) context.getBean("phonebookServiceImpl");
       MtService=(Demo_Mt)context.getBean("demo_Mt");
       System.out.println(MtService);
    }
	@Test
	public void UsersTest() throws UnsupportedEncodingException {
//		User user=new User();
//		user.setPhonenumber("13349904887");
//		user.setPassword("123456");
//		user.setMasterid(0);
//		System.out.println(phonebookService.insertUser(user));
//		System.out.println(phonebookService.isLoginSuccess("13349904887", "123456"));
//		user.setPassword("654321");
//		System.out.println(phonebookService.updateUser(user));
		
//		User user0=new User();
//		user0.setUserid(2);
//		user0.setPhonenumber("18971313472");
//		user0.setMasterid(1);
//		user0.setPassword("123456");
//		System.out.println(phonebookService.insertUser(user0));
//		System.out.println(phonebookService.isLoginSuccess("18971313472", "123456"));
//		user0.setPassword("654321");
//		System.out.println(phonebookService.updateUser(user0));
//		System.out.println(phonebookService.getUsersByMasterid(1));
//		for(User u:phonebookService.getUsersByMasterid(1).getUserslist()){
//			System.out.println(u.getPhonenumber());
//		}
		
//		User user1=new User();
//		user1.setPhonenumber("13349904884");
//		user1.setPassword("123456");
//		System.out.println(phonebookService.insertser(useUr1));
//		ContactUser user2=new ContactUser();
//		user2.setContactid(1);
//		user2.setRelationid(2);
//		System.out.println(phonebookService.replaceContactUser(user2));
//		phonebookService.getUsers();
//		phonebookService.getContactUsersByUserid(2);
//		phonebookService.updateUser(user1);
		System.out.println(phonebookService.string2MD5("123456"));
		System.out.println(phonebookService.convertMD5("123456"));
		System.out.println(phonebookService.convertMD5(phonebookService.convertMD5("123456")));
		
//		System.out.println(phonebookService.getUsers().getUserslist().get(0).getUsername());
		System.out.println(phonebookService.isEmail("hior247@ga.sdf"));
		System.out.println(phonebookService.isPhone("13349904887"));
		MtService.send("13349904887", "不败的夏天");

		System.out.println(phonebookService.isExist(phonebookService,"18696492998") );

		PF pf=new PF();
		pf.setExptime("1324");
		pf.setRanmd5("13414");
		pf.setType("234");
		pf.setUserid(141);
		System.out.println(phonebookService.insertPF(pf));
		System.out.println(phonebookService.getPFByRanmd5("13414").toString());
		System.out.println(phonebookService.removePFByuserid(141));
		//System.out.println(phonebookService.removeUser(user));
		
		//邮箱测试
		// 设置邮件服务器信息 
		MailSenderInfo mailInfo = new MailSenderInfo();  
		mailInfo.setMailServerHost("smtp.qq.com");  
		mailInfo.setMailServerPort("25");  
		mailInfo.setValidate(true);  
		// 邮箱用户名  
		mailInfo.setUserName("1062503928@qq.com");  
		// 邮箱密码  
		mailInfo.setPassword("family1314");  
		// 发件人邮箱  
		mailInfo.setFromAddress("1062503928@qq.com");  
		// 收件人邮箱  
		mailInfo.setToAddress("1021598060@qq.com");  
		// 邮件标题  
		mailInfo.setSubject("测试Java程序发送邮件");  
		// 邮件内容  
		StringBuffer buffer = new StringBuffer(); 
		buffer.append("JavaMail 1.4.5 jar包下载地址：http://www.oracle.com/technetwork/java/index-138643.html\n"); 
		buffer.append("JAF 1.1.1 jar包下载地址：http://www.oracle.com/technetwork/java/javase/downloads/index-135046.html"); 
		mailInfo.setContent(buffer.toString());   
		// 发送邮件  
		SimpleMailSender sms = new SimpleMailSender();  
		// 发送文体格式 
		sms.sendTextMail(mailInfo);  
		// 发送html格式  
		SimpleMailSender.sendHtmlMail(mailInfo);  
		System.out.println("邮件发送完毕"); 
		System.out.println(phonebookService.pwdMatcher("73"));
		}
		
	}
