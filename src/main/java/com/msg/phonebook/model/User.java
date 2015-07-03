package com.msg.phonebook.model;

public class User {
	private int userid;
	private String username;
	private String phonenumber;
	//用户头像链接
	private String imageurl;
	private int deptno;
	private String password;
	//masterid默认为0，即为真实用户，masterid为其他，表示为其他真实用户的联系人用户（可以没有密码，账号等）
	private String job;
	private String  festnetnumber;
	private String email;
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getFestnetnumber() {
		return festnetnumber;
	}
	public void setFestnetnumber(String festnetnumber) {
		this.festnetnumber = festnetnumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
