package com.msg.phonebook.service.impl;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msg.phonebook.dao.UserDAO;
import com.msg.phonebook.model.ContactUser;
import com.msg.phonebook.model.ContactUsers;
import com.msg.phonebook.model.PF;
import com.msg.phonebook.model.User;
import com.msg.phonebook.model.Users;
import com.msg.phonebook.service.PhonebookService;
@Service
public class PhonebookServiceImpl implements PhonebookService{
	@Autowired
	public UserDAO userDAO;
	public boolean isLoginSuccess(String phonenumber, String password) {
		return password.equals(userDAO.getPasswordByPhonenumber(phonenumber));
	}

	public int insertUser(User user) {
		
		return userDAO.insertUser(user);
	}

	public int updateUser(User user) {
		
		return userDAO.updateUser(user);
	}

	public int removeUser(User user) {
		
		return userDAO.removeUser(user);
	}

	public Users getUsers() {

		return userDAO.getUsers();
	}
	public ContactUsers getContactUsersByUserid(int userid) {
		return userDAO.getContactUsersByUserid(userid);
	}

	public int replaceContactUser(ContactUser contactUser) {
		return userDAO.replaceContactUser(contactUser);
	}

	 public String string2MD5(String inStr){  
	        MessageDigest md5 = null;  
	        try{  
	            md5 = MessageDigest.getInstance("MD5");  
	        }catch (Exception e){  
	            System.out.println(e.toString());  
	            e.printStackTrace();  
	            return "";  
	        }  
	        char[] charArray = inStr.toCharArray();  
	        byte[] byteArray = new byte[charArray.length];  
	  
	        for (int i = 0; i < charArray.length; i++)  
	            byteArray[i] = (byte) charArray[i];  
	        byte[] md5Bytes = md5.digest(byteArray);  
	        StringBuffer hexValue = new StringBuffer();  
	        for (int i = 0; i < md5Bytes.length; i++){  
	            int val = ((int) md5Bytes[i]) & 0xff;  
	            if (val < 16)  
	                hexValue.append("0");  
	            hexValue.append(Integer.toHexString(val));  
	        }  
	        return hexValue.toString();  
	  
	    }  
	public String convertMD5(String password) {
		  
        char[] a = password.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
	}

	public Integer getUserid(String phonenumber){
		return userDAO.getUserid(phonenumber);
	}
	public String getPasswordByPhonenumber(String phonenumber) {
		return userDAO.getPasswordByPhonenumber(phonenumber);
	}
//	public Map<String,Object> uploadImage(ByteBuffer bb) throws IOException {
//		byte[] bytes=new byte[bb.remaining()];
//		ByteArrayInputStream in=new ByteArrayInputStream(bytes);
//		BufferedImage image=ImageIO.read((InputStream)in);
//		return null;
//	}

	@Override
	public User castToUser(HashMap<String,Object> umap) {
		User user =new User();
		user.setUserid((int)umap.get("userid"));
		user.setDeptno((int)umap.get("deptno"));
		user.setEmail((String)umap.get("email"));
		user.setFestnetnumber((String)umap.get("festnetnumber"));
		user.setImageurl((String)umap.get("imageurl"));
		user.setJob((String)umap.get("job"));
		user.setPassword((String)umap.get("password"));
		user.setPhonenumber((String)umap.get("phonenumber"));
		user.setUsername((String)umap.get("username"));
		return user;
	}
	public ContactUsers castToContactUsers(HashMap<String, Object> cmap) {
			List<ContactUser> contactuserslist=(List<ContactUser>)cmap.get("contactuserslist");
			ContactUsers contactUsers=new ContactUsers();
			contactUsers.setContactuserslist(contactuserslist);
		return null;
	}

	public User getUserByUserid(int userid) {
		return userDAO.getUserByUserid(userid);
	}

	public boolean isPhone(String phonenumber) {
		Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(phonenumber);  
        b = m.matches();   
        return b;  
	}
	
	public boolean isEmail(String email) {
		Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"); // 验证邮箱  
        m = p.matcher(email);  
        b = m.matches();   
        return b;  
	}

	public boolean isExist(PhonebookService phonebookService,String username, String phonenumber){
		try{
			return phonebookService.getUserByUserid(phonebookService.getUserid(phonenumber)==null?-1:phonebookService.getUserid(phonenumber)).getUsername().equals(username);
		}catch(Exception e){
			return false;
		}
		
	}

	public boolean isExist1(PhonebookService phonebookService,String username, String email){
		
		try{
			return phonebookService.getUserByUserid(phonebookService.getUserid1(email)==null?-1:phonebookService.getUserid1(email)).getUsername().equals(username);
		}catch(Exception e){
			return false;
		}
	}

	public int insertPF(PF pf) {
		return userDAO.insertPF(pf);
	}

	public int removePFByuserid(int userid) {
		return userDAO.removePFByUserid(userid);
	}

	public PF getPFByRanmd5(String ranmd5) {
		return userDAO.getPFByRanmd5(ranmd5);
	}

	public Integer getUserid1(String email) {
		return userDAO.getUserid1(email);
	}


	public boolean pwdMatcher(String password) {
		String regex1= "^\\w{6,18}$";
		Pattern pattern1 = Pattern.compile(regex1);
		Matcher matcher1= pattern1.matcher(password);
		return matcher1.matches();
	}
}
