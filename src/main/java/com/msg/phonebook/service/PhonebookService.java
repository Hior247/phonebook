package com.msg.phonebook.service;

import com.msg.phonebook.model.ContactUser;
import com.msg.phonebook.model.ContactUsers;
import com.msg.phonebook.model.User;
import com.msg.phonebook.model.Users;


public interface PhonebookService {
	public boolean isLoginSuccess(String phonenumber,String password);
	public int insertUser(User user);
	public int updateUser(User user);
	public int removeUser(User user);
	public Users getUsers();
	public ContactUsers getContactUsersByUserid(int userid);
	public int replaceContactUser(ContactUser contactUser);
	

}
