<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	 <form action="findPassword.do" name="resetPassword" method="post" onsubmit='return check();' >
		<input type=password  name="password1" id="password1"/>
		<input type=password  name="password2" id="password2"/><!-- <%="#{p1}"=="#{p2}"?"":"两次输入密码不等" %> -->
		<input type=hidden value="${phonenumber}" name="phonenumber"/>
		<input type=hidden value="${email}" name="email"/> 
		<input type="submit" id="sub"/>
	</form> 
</body>
<script>


function check(){
//	var patt=/^\w{6,18}$/;
	var password = document.getElementById("password1");
	var passwordConfirm = document.getElementById("password2");
	if(password.value != passwordConfirm.value){
		alert("对不起，您2次输入的密码不一致");
		return false;
	}else if(passwordConfirm.value==""){
		alert("对不起，您输入的密码为空");
		return false;
	}
//	else if(!patt.test(passwordConfirm)){
//		alert("对不起，您输入的密码格式有误");
//		return false;  
//	}
	else{
		document.forms[0].submit();
	}
	
}
</script>
</html>
