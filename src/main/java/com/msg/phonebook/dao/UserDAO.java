package com.msg.phonebook.dao;

import com.msg.phonebook.model.ContactUser;
import com.msg.phonebook.model.ContactUsers;
import com.msg.phonebook.model.PF;
import com.msg.phonebook.model.User;
import com.msg.phonebook.model.Users;

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
