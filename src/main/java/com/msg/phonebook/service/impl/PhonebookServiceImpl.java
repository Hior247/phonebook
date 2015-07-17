package com.msg.phonebook.service.impl;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msg.phonebook.dao.UserDAO;
import com.msg.phonebook.model.ContactUser;
import com.msg.phonebook.model.ContactUsers;
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

	public int getUserid(String phonenumber){
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
}
