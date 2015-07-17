package com.msg.phonebook.model;

import java.util.List;

public class Users {
	private List<User> userslist=null;
	private List<ContactUser> contactuserlist=null;
	
	public List<ContactUser> getContactuserlist() {
		return contactuserlist;
	}

	public void setContactuserlist(List<ContactUser> contactuserlist) {
		this.contactuserlist = contactuserlist;
	}

	public List<User> getUserslist() {
		return userslist;
	}

	public void setUserslist(List<User> userslist) {
		this.userslist = userslist;
	}
	
}
