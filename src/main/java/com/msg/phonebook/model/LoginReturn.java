package com.msg.phonebook.model;


public class LoginReturn  {
	private boolean success;//是否成功
	private Object msg;//返回的消息
	private Object otherObject;//其他对象
	/**
	 *是否登陆成功的构造方法
	 *
	 *@param success
	 *						是否成功
	 *@param msg
	 *						消息
	 */
	
	public LoginReturn(boolean success,Object msg){
		this.success=success;
		this.msg=msg;
		this.otherObject="";
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	public Object getOtherObject() {
		return otherObject;
	}
	public void setOtherObject(Object otherObject) {
		this.otherObject = otherObject;
	}
	
}
