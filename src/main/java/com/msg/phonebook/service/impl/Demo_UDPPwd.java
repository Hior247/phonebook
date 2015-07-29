package com.msg.phonebook.service.impl;
import java.io.UnsupportedEncodingException;

import com.msg.phonebook.service.impl.Client;


public class Demo_UDPPwd {
//该Demo是更改密�?
	public static void main()throws UnsupportedEncodingException{
		//输入您的软件序列号和密码
		String sn="SDK-MOV-010-00635";
		String pwd="247985";

		Client client=new Client(sn,pwd);
		
		
		String result_charge = client.UDPPwd("173736");
		System.out.println(result_charge);
	}
}
