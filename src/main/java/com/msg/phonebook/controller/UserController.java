package com.msg.phonebook.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.msg.phonebook.model.ContactUser;
import com.msg.phonebook.model.ContactUsers;
import com.msg.phonebook.model.LoginReturn;
import com.msg.phonebook.model.User;
import com.msg.phonebook.service.PhonebookService;

@Controller
@RequestMapping("/")
public class UserController {
	/**
	 * 注入phonebookService
	 * @before 定义方法执行前实例化phonebookService
	 */
	@Autowired
	private PhonebookService phonebookService;
	@Before
    public void before(){                                                                   
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml", 
                "classpath:conf/spring-mybatis.xml"});
       phonebookService = (PhonebookService) context.getBean("phonebookServiceImpl");
       System.out.println("111");
    }
	/**
	 * regist.do
	 * 注册
	 * @param user
	 * @param out
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/regist",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String,Object> regist(@RequestBody Map<String,User> smap,PrintWriter out,HttpServletResponse response){
		Map<String,Object> map=new HashMap<String,Object>();
		User user=smap.get("user");
		try{
			phonebookService.insertUser(user);
		}catch(Exception e){
			response.reset();
			map.put("msg", "failure");
			return map;
		}
		response.reset();
		map.put("msg", "success");
		return map;
	}
	/**
	 * login.do
	 * 登陆
	 * @param phonenumber
	 * @param password
	 * @param out
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/login",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public LoginReturn login(@RequestBody Map<String,Object> smap,PrintWriter out,HttpServletResponse response){
//		String phonenumber=(String)request.getAttribute("phonenumber");
//		String password=(String)request.getAttribute("password");
		String phonenumber=(String)smap.get("phonenumber");
		String password=(String)smap.get("password");
		boolean flag=phonebookService.isLoginSuccess(phonenumber, password);
		String msg=flag?"登陆成功":"用户名或密码错误";
		LoginReturn lr=new LoginReturn(flag,msg);
		response.reset();
		return lr;

	}
	/**
	 * iport.do
	 * 导入到前端
	 * @param userid
	 * @param out
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/iport",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String,Object> iport(@RequestBody Map<String,Object> smap,PrintWriter out,HttpServletResponse response){
		int userid=(Integer)smap.get("userid");
		response.reset();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", phonebookService.getUsers());
		map.put("contactUsers", phonebookService.getContactUsersByUserid(userid));
		return map;
	}
	/**
	 * eport.do
	 * 导出到服务端
	 * @param users
	 * @param userid
	 * @param out
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/eport",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public @ResponseBody Map<String,Object> eport(@RequestBody Map<String,Object> smap,PrintWriter out,HttpServletResponse response){
		ContactUsers contactUsers=(ContactUsers)smap.get("contactUsers");
		User user=(User)smap.get("user");
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			for(ContactUser contactUser:contactUsers.getContactuserslist()){
				phonebookService.replaceContactUser(contactUser);
			}
			phonebookService.updateUser(user);
			response.reset();
			map.put("msg","success");
			return map;
		}catch(Exception e){
			map.put("msg", "failure");
			response.reset();
			return map;
		}
	}
	
	@RequestMapping("index")
    public String index(){
        return "index";
	}
	
}
