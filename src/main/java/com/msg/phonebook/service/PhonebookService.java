package com.msg.phonebook.service;

import java.util.HashMap;

import com.msg.phonebook.model.ContactUser;
import com.msg.phonebook.model.ContactUsers;
import com.msg.phonebook.model.PF;
import com.msg.phonebook.model.User;
import com.msg.phonebook.model.Users;


public interface PhonebookService {
	public boolean isLoginSuccess(String phonenumber,String password);
	public int insertUser(User user);
	public int updateUser(User user);
	public int removeUser(User user);
	public Integer getUserid(String phonenumber);
	public Integer getUserid1(String email);
	public Users getUsers();
	public ContactUsers getContactUsersByUserid(int userid);
	public int replaceContactUser(ContactUser contactUser); 
	public String convertMD5(String password);
	public String getPasswordByPhonenumber(String phonenumber);
	 public  String string2MD5(String inStr);
	 public User castToUser(HashMap<String,Object> umap);
	 public ContactUsers castToContactUsers(HashMap<String,Object> cmap);
	 public User getUserByUserid(int userid);
	 public boolean isPhone(String phonenumber);
	 public boolean isEmail(String email);
	 public boolean isExist(PhonebookService phonebookService,String phonenumber);
	 public boolean isExist1(PhonebookService phonebookService,String email);
	 public int insertPF(PF pf);
	 public int removePFByuserid(int userid);
	 public PF getPFByRanmd5(String ranmd5);
	 public boolean pwdMatcher(String password);
}
