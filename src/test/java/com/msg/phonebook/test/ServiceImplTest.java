package com.msg.phonebook.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.msg.phonebook.model.ContactUser;
import com.msg.phonebook.model.User;
import com.msg.phonebook.service.PhonebookService;

public class ServiceImplTest {
	@	Autowired
	private PhonebookService phonebookService;
	@Before
    public void before(){                                                                   
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml", 
                "classpath:conf/spring-mybatis.xml"});
       phonebookService = (PhonebookService) context.getBean("phonebookServiceImpl");
    }
	@Test
	public void UsersTest() {
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
//		System.out.println(phonebookService.insertUser(user1));
//		ContactUser user2=new ContactUser();
//		user2.setContactid(1);
//		user2.setRelationid(2);
//		System.out.println(phonebookService.replaceContactUser(user2));
//		phonebookService.getUsers();
//		phonebookService.getContactUsersByUserid(2);
//		phonebookService.updateUser(user1);
//		System.out.println(phonebookService.string2MD5("123456"));
//		System.out.println(phonebookService.convertMD5("123456"));
//		System.out.println(phonebookService.convertMD5(phonebookService.convertMD5("123456")));
		
		System.out.println(phonebookService.getUsers().getUserslist().get(0).getUsername());
		
		//System.out.println(phonebookService.removeUser(user));
	}
}
