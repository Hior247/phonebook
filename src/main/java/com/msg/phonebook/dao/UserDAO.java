package com.msg.phonebook.dao;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msg.phonebook.model.ContactUser;
import com.msg.phonebook.model.ContactUsers;
import com.msg.phonebook.model.PF;
import com.msg.phonebook.model.User;
import com.msg.phonebook.model.Users;
import com.msg.phonebook.service.impl.Demo_Mt;
import com.msg.phonebook.service.impl.Demo_Sm;

public interface UserDAO {
	public int insertUser(User user);
	public int updateUser(User user);
	public int removeUser(User user);
	public Integer getUserid(String phonenumber);
	public int getUserid1(String email);
	public String getPasswordByPhonenumber(String phonenumber);
	public Users getUsers();
	public ContactUsers getContactUsersByUserid(int userid);
	public int replaceContactUser(ContactUser contactUser);
	public User getUserByUserid(int userid);
	public int removePFByUserid(int userid);
	public int insertPF(PF pf);
	public PF getPFByRanmd5(String ranmd5);

}
