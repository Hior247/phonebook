package com.msg.phonebook.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.springframework.stereotype.Service;



/**
 * 发送短信
 * 
 */
@Service
public class Demo_Mt {
	
	private String sn = "SDK-MOV-010-00635";
	private String pwd = "247985";
	private String shouji;
	
	public void send(String s,String mycontent) throws UnsupportedEncodingException {
		shouji = s;
		// 输入软件序列号和密码
		Client client = new Client(sn, pwd);
		
		int y = (int)(Math.random()*899999+100000);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		
	//	SharedPreferences preferences = context.getSharedPreferences("yzm", Context.MODE_PRIVATE);
		//SharedPreferences.Editor editor = preferences.edit();
	/*	editor.putString("yan", y+"");
		editor.putInt("year", year);
		editor.putInt("month", month);
		editor.putInt("day", day);
		editor.putInt("hour", hour);
		editor.putInt("minute", minute);
		editor.commit();*/

		
		String content =   mycontent+"【爱社区】";
		// 我们的Demo最后是拼成xml了，所以要按照xml的语法来转义
		if (content.indexOf("&") >= 0) {
			content = content.replace("&", "&amp;");
		}

		if (content.indexOf("<") >= 0) {

			content = content.replace("<", "&lt;");

		}
		if (content.indexOf(">") >= 0) {
			content = content.replace(">", "&gt;");
		}

		// 短信发送
		String result_mt = client.mt(shouji, content, "", "", "");
		if (result_mt.startsWith("-") || result_mt.equals(""))// 以负号判断是否发送成功
		{
			System.out.print("发送失败！返回值为：" + result_mt + "。请查看webservice返回值对照表");
			return;
		}
		// 输出返回标识，为小于19位的正数，String类型的，记录您发送的批次
		else {
			System.out.print("发送成功，返回值为：" + result_mt);
		}
	}

}
