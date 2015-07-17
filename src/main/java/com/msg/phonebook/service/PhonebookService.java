package com.msg.phonebook.service;

import java.util.HashMap;

import com.msg.phonebook.model.ContactUser;
import com.msg.phonebook.model.ContactUsers;
import com.msg.phonebook.model.User;
import com.msg.phonebook.model.Users;


public interface PhonebookService {
	public boolean isLoginSuccess(String phonenumber,String password);
	public int insertUser(User user);
	public int updateUser(User user);
	public int removeUser(User user);
	public int getUserid(String phonenumber);
	public Users getUsers();
	public ContactUsers getContactUsersByUserid(int userid);
	public int replaceContactUser(ContactUser contactUser); 
	public String convertMD5(String password);
	public String getPasswordByPhonenumber(String phonenumber);
	 public  String string2MD5(String inStr);
	 public User castToUser(HashMap<String,Object> umap);
	 public ContactUsers castToContactUsers(HashMap<String,Object> cmap);
	 public User getUserByUserid(int userid);
}
