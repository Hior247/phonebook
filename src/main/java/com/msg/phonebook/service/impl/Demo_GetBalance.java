package com.msg.phonebook.service.impl;
import java.io.UnsupportedEncodingException;

import com.msg.phonebook.service.impl.Client;

/**
 * 查询余额
 * @author acer
 *
 */
public class Demo_GetBalance {
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		//输入软件序列号和密码

		String sn="SDK-BBX-010-08506";
		String pwd="362565";
		Client client=new Client(sn,pwd);
		
		//查询余额
		String result_balance = client.getBalance();
		System.out.print("您的余额为 : "+result_balance);
	}
}
