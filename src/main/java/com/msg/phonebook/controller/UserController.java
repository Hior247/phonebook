package com.msg.phonebook.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
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
	private ObjectMapper objectMapper;
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
	public Map<String,Object> regist(@RequestBody User user,PrintWriter out,HttpServletResponse response){
		Map<String,Object> map=new HashMap<String,Object>();
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
			String uploadPath=request.getSession().getServletContext().getRealPath("/avatar");
			
			System.out.println("-->>"+uploadPath);
			File file=new File(uploadPath);
			if(!file.exists()){
				file.mkdir();
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
			String halfurl=filename;
			filename=(uploadPath+"/"+filename);
			if(new File(filename).exists()){
				new File(filename).delete();
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
					user.setImageurl(wRoot+"avatar"+"/"+halfurl);
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
				file.mkdir();
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
			if(files.length>=5){
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
					user.setVcfurl(wRoot+"neptunus"+"/"+userid+"/"+halfurl);
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
	@RequestMapping("index")
public String index(){
        return "index";
	}
}