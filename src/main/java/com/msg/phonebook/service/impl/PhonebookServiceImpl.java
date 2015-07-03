package com.msg.phonebook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msg.phonebook.dao.UserDAO;
import com.msg.phonebook.model.ContactUser;
import com.msg.phonebook.model.ContactUsers;
import com.msg.phonebook.model.User;
import com.msg.phonebook.model.Users;
import com.msg.phonebook.service.PhonebookService;
@Service
public class PhonebookServiceImpl implements PhonebookService{
	@Autowired
	public UserDAO userDAO;
	public boolean isLoginSuccess(String phonenumber, String password) {
		return password.equals(userDAO.getPasswordByPhonenumber(phonenumber));
	}

	public int insertUser(User user) {
		
		return userDAO.insertUser(user);
	}

	public int updateUser(User user) {
		
		return userDAO.updateUser(user);
	}

	public int removeUser(User user) {
		
		return userDAO.removeUser(user);
	}

	public Users getUsers() {

		return userDAO.getUsers();
	}
	public ContactUsers getContactUsersByUserid(int userid) {
		return userDAO.getContactUsersByUserid(userid);
	}

	public int replaceContactUser(ContactUser contactUser) {
		return userDAO.replaceContactUser(contactUser);
	}

	
}
