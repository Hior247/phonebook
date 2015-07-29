package com.msg.phonebook.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.msg.phonebook.model.PF;
import com.msg.phonebook.model.User;
import com.msg.phonebook.service.PhonebookService;
import com.msg.phonebook.service.impl.Demo_Mt;
import com.msg.phonebook.service.impl.Demo_Sm;

@Controller
@RequestMapping("/")
public class UserController {
	/**
	 * 注入phonebookService
	 * @before 定义方法执行前实例化phonebookService
	 */
	@Autowired
	private PhonebookService phonebookService;
	private ObjectMapper objectMapper;
	private Demo_Mt MtService;
	private Demo_Sm SmService;
	private String type1="1";
	private String type2="2";
	@Before
    public void before(){                                                                   
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml", 
                "classpath:conf/spring-mybatis.xml"});
       phonebookService = (PhonebookService) context.getBean("phonebookServiceImpl");
      	MtService=(Demo_Mt)context.getBean("demo_Mt");
      	System.out.println(MtService);
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
	public Map<String,Object> regist(@RequestBody User user,PrintWriter out,HttpServletResponse response){
		Map<String,Object> map=new HashMap<String,Object>();
		//用户信息校验
		String msgstr="";
		if(!phonebookService.isPhone(user.getPhonenumber())){
			msgstr="phonenumberError";
		}
		if(user.getEmail()!=""&&user.getEmail()!=null&&!phonebookService.isEmail(user.getEmail())){
			msgstr=msgstr+" emailError";
		}
		if(user.getPassword()!=""&&user.getPassword()!=null&&!phonebookService.pwdMatcher(user.getPassword())){
			msgstr=msgstr+" passwordError";
		}
		if(!phonebookService.isPhone(user.getPhonenumber())||(user.getEmail()!=""&&user.getEmail()!=null&&!phonebookService.isEmail(user.getEmail()))||(user.getPassword()!=""&&user.getPassword()!=null&&!phonebookService.pwdMatcher(user.getPassword()))){
			response.reset();
			map.put("msg", msgstr);
			return map;
		}
		try{
			user.setPassword(phonebookService.string2MD5(user.getPassword()));
			phonebookService.insertUser(user);
			response.reset();
			map.put("msg", "success");
			return map;
		}catch(Exception e){
			response.reset();
			map.put("msg", "failure:The phonenumber already exists or your password is empty");
			return map;
		}
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
	public Map<String,Object> login(@RequestBody Map<String,Object> smap,PrintWriter out,HttpServletResponse response){
//		String phonenumber=(String)request.getAttribute("phonenumber");
//		String password=(String)request.getAttribute("password");
		Map<String,Object> map=new HashMap<String,Object>();
		String phonenumber=(String)smap.get("phonenumber");
		String password=(String)smap.get("password");
		boolean flag=phonebookService.isLoginSuccess(phonenumber, phonebookService.string2MD5(password));
		String msg=flag?"success":"failure";
		if(flag){
			int userid=phonebookService.getUserid(phonenumber);
			map.put("userid", userid);
		}
		response.reset();
		map.put("msg", msg);
		return map;

	}
	/**
	 * 
	 * @param userid
	 * @param out
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="uploadImage",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String,Object> uploadImage(@RequestParam("userid") int userid,PrintWriter out,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map=new HashMap<String,Object>();
		String wRoot="http://localhost:8080/phonebook/";
		try{
			request.setCharacterEncoding("UTF-8");//设置处理请求的编码格式
			System.out.println(userid);
			//存储头像地址到数据库
//			try{
//				User  user=phonebookService.getUserByUserid(userid);
//				System.out.println(1);
//				user.setImageurl(userid+".jpg");
//				System.out.println(1);
//				phonebookService.updateUser(user);
//				System.out.println(1);
//			}catch(Exception e){
//				System.out.println(e.toString());
//			}
			DiskFileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			List<FileItem> items=upload.parseRequest(request);
			//根据起的特殊名称avatar以及userid命名所在文件夹
			String uploadPath=request.getSession().getServletContext().getRealPath("/avatar"+"/"+userid);
			//获取上传时间
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			String datestr=sdf.format(date);
			System.out.println(datestr);
			System.out.println("-->>"+uploadPath);
			File file=new File(uploadPath);
			System.out.println("11111111");
			System.out.println(file);
			if(!file.exists()){
				file.mkdirs();
			}
			String filename="";
			InputStream is=null;
			for(FileItem item:items){
				// 处理普通的表单域
				if (item.isFormField())
				{
					if (item.getFieldName().equals("filename"))
					{
						// 如果新文件不为空，将其保存在filename中
						if (!item.getString().equals(""))
							filename = item.getString("UTF-8");
					}
				}
				// 处理上传文件
				else if (item.getName() != null && !item.getName().equals(""))
				{
					// 从客户端发送过来的上传文件路径中截取文件名
					filename = item.getName();
					//File real_path = new File(uploadPath + "/" + filename);
					//item.write(real_path);
					is = item.getInputStream(); // 得到上传文件的InputStream对象
				}
			}
			String halfurl=datestr+".jpg";
			filename=(uploadPath+"/"+halfurl);
			//判断文件夹下面文件个数，若大于等于五则把前面的删除
			
			File[] files=new File(uploadPath).listFiles();
			//files有可能为空
			System.out.println("files");
			if(files==null){
				System.out.println(files);
			}else if(files.length>=5){
				System.out.println(11);
				for(int i=0;i<files.length-4;i++){
					files[i].delete();
				}
			}//开始上传文件
				if (!filename.equals(""))
				{
					// 用FileOutputStream打开服务端的上传文件
					//DeferredFileOutputStream fos = new DeferredFileOutputStream(filename);
					FileOutputStream fos = new FileOutputStream(filename);
					byte[] buffer = new byte[8192]; // 每次读8K字节
					int count = 0;
					// 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
					while ((count = is.read(buffer)) > 0)
					{
						fos.write(buffer, 0, count); // 向服务端文件写入字节流
						
					}
					fos.close(); // 关闭FileOutputStream对象
					is.close(); // InputStream对象
					map.put("msg", "upload success!");
				
//					http://localhost:8080/phonebook/
					User  user=new User();
					try{
					user=phonebookService.getUserByUserid(userid);
					System.out.println(1);
					
					String allFile="";
					//获取文件夹下的所有文件名
					File[] files2=new File(uploadPath).listFiles();
					if(files2==null){
						System.out.println(files2);
					}else{
						for(int i=0;i<files2.length;i++){
							allFile=allFile+","+files2[i].getName();
						}
					}
					
					user.setImageurl(wRoot+"avatar"+"/"+userid+"/"+allFile);
					System.out.println(1);
					phonebookService.updateUser(user);
					System.out.println(1);
				}catch(Exception e){
					System.out.println(e.toString());
				}
					map.put("imageurl",user.getImageurl());
					response.reset();
					return map;
			}
		}catch(Exception e){
			System.out.println(e);
			map.put("msg", "upload Exception");
			response.reset();
			return map;
		}
		map.put("msg", "upload failure");
		return map;
	}
	/**
	 * iport.do
	 * 导入到前端
	 * @param userid
	 * @param out
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/iport",method=RequestMethod.POST,produces="application/josn;charset=UTF-8")
	@ResponseBody
	public Map<String,Object> iport(PrintWriter out,HttpServletResponse response){
//		int userid=(Integer)smap.get("userid");
		response.reset();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", phonebookService.getUsers());
//		map.put("contactUsers", phonebookService.getContactUsersByUserid(userid));
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
	public @ResponseBody Map<String,Object> eport(@RequestParam int userid,PrintWriter out,HttpServletResponse response,HttpServletRequest request){
			Map<String,Object> map=new HashMap<String,Object>();
			//后面根据ipconfig将localhost改为相应ip传给前端
			String wRoot="http://localhost:8080/phonebook/";
//			contactUsers=objectMapper.readValue(smap.get("contactUsers"),ContactUsers.class);
//			user=objectMapper.readValue(smap.get("user"),User.class);
		try{
			request.setCharacterEncoding("UTF-8");//设置处理请求的编码格式
			System.out.println(userid);
			//存储头像地址到数据库
//			try{
//				User  user=phonebookService.getUserByUserid(userid);
//				System.out.println(1);
//				user.setImageurl(userid+".jpg");
//				System.out.println(1);
//				phonebookService.updateUser(user);
//				System.out.println(1);
//			}catch(Exception e){
//				System.out.println(e.toString());
//			}
			DiskFileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			List<FileItem> items=upload.parseRequest(request);
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			String datestr=sdf.format(date);
			System.out.println(datestr);
			
			String uploadPath=request.getSession().getServletContext().getRealPath("/neptunus"+"/"+userid);
			
			System.out.println("-->>"+uploadPath);
			File file=new File(uploadPath);
			if(!file.exists()){
				file.mkdirs();
			}
			String filename="";
			InputStream is=null;
			for(FileItem item:items){
				// 处理普通的表单域
				if (item.isFormField())
				{
					if (item.getFieldName().equals("filename"))
					{
						// 如果新文件不为空，将其保存在filename中
						if (!item.getString().equals(""))
							filename = item.getString("UTF-8");
					}
				}
				// 处理上传文件
				else if (item.getName() != null && !item.getName().equals(""))
				{
					// 从客户端发送过来的上传文件路径中截取文件名
					filename = item.getName();
					//File real_path = new File(uploadPath + "/" + filename);
					//item.write(real_path);
					is = item.getInputStream(); // 得到上传文件的InputStream对象
				}
			}
			String halfurl=datestr+".vcf";
			filename=(uploadPath+"/"+halfurl);
			//判断文件夹下面文件个数，若大于等于五则把前面的删除
			File[] files=new File(uploadPath).listFiles();
			if(files==null){
				System.out.println(files);
			}else if(files.length>=5){
				for(int i=0;i<files.length-4;i++){
					files[i].delete();
				}
			}//开始上传文件
				if (!filename.equals(""))
				{
					// 用FileOutputStream打开服务端的上传文件
					//DeferredFileOutputStream fos = new DeferredFileOutputStream(filename);
					FileOutputStream fos = new FileOutputStream(filename);
					byte[] buffer = new byte[8192]; // 每次读8K字节
					int count = 0;
					// 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
					while ((count = is.read(buffer)) > 0)
					{
						fos.write(buffer, 0, count); // 向服务端文件写入字节流
						
					}
					fos.close(); // 关闭FileOutputStream对象
					is.close(); // InputStream对象
					map.put("msg", "upload success!");
				
//					http://localhost:8080/phonebook/
					User  user=new User();
					try{
					user=phonebookService.getUserByUserid(userid);
					System.out.println(1);
					String allFile="";
					//获取文件夹下的所有文件名
					File[] files2=new File(uploadPath).listFiles();
					if(files2==null){
						System.out.println(files2);
					}else{
						for(int i=0;i<files2.length;i++){
							allFile=allFile+","+files2[i].getName();
						}
					}
					
					user.setVcfurl(wRoot+"neptunus"+"/"+userid+"/"+allFile);
					System.out.println(1);
					phonebookService.updateUser(user);
					System.out.println(1);
				}catch(Exception e){
					System.out.println(e.toString());
				}
					map.put("vcfurl",user.getVcfurl());
					response.reset();
					return map;
			}
		}catch(Exception e){
			System.out.println(e);
			map.put("msg", "upload Exception");
			response.reset();
			return map;
		}
		map.put("msg", "upload failure");
		return map;
	}
	@RequestMapping(value="updateUser",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String,Object> updateUser(@RequestBody User user,PrintWriter out,HttpServletResponse response){
		
		Map<String,Object> map=new HashMap<String,Object>();
		//用户信息校验
				String msgstr="";
				if(!phonebookService.isPhone(user.getPhonenumber())){
					msgstr="phonenumberError";
				}
				if(user.getEmail()!=""&&user.getEmail()!=null&&!phonebookService.isEmail(user.getEmail())){
					msgstr=msgstr+" emailError";
				}
				if(user.getPassword()!=""&&user.getPassword()!=null&&!phonebookService.pwdMatcher(user.getPassword())){
					msgstr=msgstr+" passwordError";
				}
				if(!phonebookService.isPhone(user.getPhonenumber())||(user.getEmail()!=""&&user.getEmail()!=null&&!phonebookService.isEmail(user.getEmail()))||(user.getPassword()!=""&&user.getPassword()!=null&&!phonebookService.pwdMatcher(user.getPassword()))){
					response.reset();
					map.put("msg", msgstr);
					return map;
				}
		try{
			user.setPassword(phonebookService.string2MD5(user.getPassword()));
			phonebookService.updateUser(user);
			response.reset();
			map.put("msg", "success");
			return map;
		}catch(Exception e){
			response.reset();
			map.put("msg", "failure");
			return map;
		}

	}
	
	@RequestMapping(value="sendUrl",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody 
	public Map<String,Object> sendUrl(@RequestBody	Map<String,Object> smap,PrintWriter out,HttpServletResponse response){
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml", 
        "classpath:conf/spring-mybatis.xml"});
		MtService=(Demo_Mt)context.getBean("demo_Mt");
		SmService=(Demo_Sm)context.getBean("demo_Sm");
		System.out.println(MtService);
		System.out.println("111");
		//type判断
		String type=(String)smap.get("type");
		if(type.equals(type1)){
			String username=(String)smap.get("username");
			String phonenumber=(String)smap.get("phonenumber");
			Map<String,Object> map=new HashMap<String,Object>();
		try {
	
		
		//用户信息校验
				String msgstr="";
				if(!phonebookService.isExist(phonebookService, username, phonenumber)){
					msgstr="phonenumberError or usernameError";
					map.put("msg",msgstr);
					response.reset();
					return map;
				}
				//  删除忘记密码表中  user_id = $user_id的 记录 
				//	并生成一个随机md5值     插入一条新记录
				//	然后把md5值加到url里    发送给用户

				int userid=phonebookService.getUserid(phonenumber);
				Date date=new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
				String ranmd5=phonebookService.convertMD5(sdf.format(date));
				System.out.println(ranmd5);
				phonebookService.removePFByuserid(userid);
				PF pf=new PF();
				pf.setUserid(userid);
				pf.setRanmd5(ranmd5);
				//ms为单位
				pf.setExptime("1800000");
				pf.setType("1");
				//后期需要分析type
				phonebookService.insertPF(pf);
				String resetPwdUrl="http://192.168.31.225:8080/phonebook/p_resetpwd.do?phonenumber="+phonenumber+"&ranmd5="+ranmd5;
				String mycontent="请您点击链接完成密码重置"+resetPwdUrl+"谢谢您对爱社区的支持";
				
				
					System.out.println(MtService);
					MtService.send(phonenumber, mycontent);
					System.out.println(mycontent);
					map.put("msg", "send message success");
					response.reset();
					return map;
				} catch (UnsupportedEncodingException e) {
					map.put("msg", "send message failure");
					response.reset();
					return map;
				}
		}else {
			
			String username=(String)smap.get("username");
			String email=(String)smap.get("email");
			Map<String,Object> map=new HashMap<String,Object>();
			try {
			//用户信息校验
					String msgstr="";
					if(!phonebookService.isExist1(phonebookService, username, email)){
						msgstr="emailError or usernameError";
						map.put("msg",msgstr);
						response.reset();
						return map;
					}
					//  删除忘记密码表中  user_id = $user_id的 记录 
					//	并生成一个随机md5值     插入一条新记录
					//	然后把md5值加到url里    发送给用户

					int userid=phonebookService.getUserid1(email);
					Date date=new Date();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
					String ranmd5=phonebookService.convertMD5(sdf.format(date));
					System.out.println(ranmd5);
					phonebookService.removePFByuserid(userid);
					PF pf=new PF();
					pf.setUserid(userid);
					pf.setRanmd5(ranmd5);
					//ms为单位
					pf.setExptime("1800000");
					pf.setType("1");
					//后期需要分析type
					phonebookService.insertPF(pf);
					
					String resetPwdUrl="http://192.168.31.225:8080/phonebook/m_resetpwd.do?email="+email+"&ranmd5="+ranmd5;
					String mycontent="请您点击链接完成密码重置"+resetPwdUrl+"谢谢您对爱社区的支持";
					
						System.out.println(SmService);
						SmService.send(email, mycontent);
						System.out.println(mycontent);
						map.put("msg", "send message success");
						response.reset();
						return map;
					} catch (UnsupportedEncodingException e) {
						map.put("msg", "send message failure");
						response.reset();
						return map;
					}
			
		}
	}
	@RequestMapping(value="p_resetpwd",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public ModelAndView p_resetpwd(@RequestParam("phonenumber") String phonenumber,@RequestParam("ranmd5") String ranmd5,HttpServletResponse response){
			Date date2=new Date();
			Date date1=date2;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			
			 Map<String, Object> context = new HashMap();  

			 ModelAndView mav=new ModelAndView();
			    
			try {
				date1=sdf.parse(phonebookService.convertMD5(ranmd5));
			} catch (Exception e) {
				e.printStackTrace();
			}
			Long time=date2.getTime()-date1.getTime();
			if(time==0||time>1800000||(phonebookService.getPFByRanmd5(ranmd5)==null)){
			 mav.setViewName("Ranmd5error");
			 //过期删除PF记录
			 phonebookService.removePFByuserid(phonebookService.getUserid(phonenumber));
			  return mav;		
			}
			 context.put("phonenumber", phonenumber); 
			 mav.setViewName("resetpwd");
			 mav.addAllObjects(context);
			 return mav;
		}
	@RequestMapping(value="m_resetpwd",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public ModelAndView m_resetpwd(@RequestParam("email") String email,@RequestParam("ranmd5") String ranmd5,HttpServletResponse response){
			Date date2=new Date();
			Date date1=date2;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			
			 Map<String, Object> context = new HashMap();  

			 ModelAndView mav=new ModelAndView();
			    
			try {
				date1=sdf.parse(phonebookService.convertMD5(ranmd5));
			} catch (Exception e) {
				e.printStackTrace();
			}
			Long time=date2.getTime()-date1.getTime();
			if(time==0||time>1800000||(phonebookService.getPFByRanmd5(ranmd5)==null)){
			 mav.setViewName("Ranmd5error");
			 //过期删除PF记录
			 phonebookService.removePFByuserid(phonebookService.getUserid1(email));
			  return mav;		
			}
			 context.put("email", email); 
			 mav.setViewName("resetpwd");
			 mav.addAllObjects(context);
			 return mav;
		}
	
	@RequestMapping(value="findPassword",method=RequestMethod.POST,produces="text/html;charset=utf-8")
	//没法解析成requestBody的形式
	public String  findPassword(HttpServletRequest request,PrintWriter out,HttpServletResponse response){
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml", 
        "classpath:conf/spring-mybatis.xml"});
		MtService=(Demo_Mt)context.getBean("demo_Mt");
		System.out.println(MtService);
		System.out.println("111");
		
//		Map<String,Object> map=new HashMap<String,Object>();
		
//		String phonenumber=(String)smap.get("phonenumber");
//		String 	password=(String)smap.get("password");
		String email=(String)request.getParameter("email");
		String phonenumber=(String)request.getParameter("phonenumber");
		String 	password=(String)request.getParameter("password2");
		String mycontent1="您已经完成密码重置,您的新密码是"+password+"谢谢您对爱社区的支持";
		String mycontent2="密码重置失败,请重试"+"谢谢您对爱社区的支持";
		User user;
		try {
			if(phonenumber!=""){
				user=phonebookService.getUserByUserid(phonebookService.getUserid(phonenumber));
			}else{
				user=phonebookService.getUserByUserid(phonebookService.getUserid1(email));
			}
			user.setPassword(phonebookService.string2MD5(password));
			phonebookService.updateUser(user);
//			map.put("msg", " find success");
			if(phonenumber!=""){
				MtService.send(phonenumber, mycontent1);
			}else{
				SmService.send(email, mycontent1);
			}
			//密码重置完成把PF记录删掉
			if(phonenumber!=""){
				phonebookService.removePFByuserid(phonebookService.getUserid(phonenumber));
			}else{
				phonebookService.removePFByuserid(phonebookService.getUserid1(email));
			}
			response.reset();
			return "findSuccess";
		} catch (Exception e) {
//			map.put("msg", "find failure");
			try {
				
				if(phonenumber!=""){
					MtService.send(phonenumber, mycontent2);
				}else{
					SmService.send(email, mycontent2);
				}
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			response.reset();
			return "findFailure";
		}
		
	}
	@RequestMapping("index")
public String index(){
        return "index";
	}
}