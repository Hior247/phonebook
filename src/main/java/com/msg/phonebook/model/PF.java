package com.msg.phonebook.model;

public class PF {
	private int userid;
	private String ranmd5;
	private String exptime;
	private String type;
	private int pfid;
	public int getPfid() {
		return pfid;
	}
	public void setPfid(int pfid) {
		this.pfid = pfid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getRanmd5() {
		return ranmd5;
	}
	public void setRanmd5(String ranmd5) {
		this.ranmd5 = ranmd5;
	}
	public String getExptime() {
		return exptime;
	}
	public void setExptime(String exptime) {
		this.exptime = exptime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
