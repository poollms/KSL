<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!DOCTYPE html> <html> <head> <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 

<!-- 스프링 context이용해서 아름답게 짜는방법이 있음 -->


<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.7.2.min.js'/>" charset="UTF-8"></script> 

<script type="text/javascript"> 
	$(document).ready(function(){
		
		/* if you click btn, go to the controller */
		 $("#execute").click(function(){  
			
			$.ajax({
					url : "/createCode.do",
					dataType : "json",
					type : "post",
					data :{ "content" : $("#content").val() },
					success : function(data) {
						alert("QrUrl is :" + data.qrUrl);
						$("#img").attr("src", data.qrUrl);
					},
					error : function(request, status, error) {
						alert("code:" + request.status + "\n" + "error:" + error);
					}
				});

		});  


	});

	//qrUrl
</script> 

<title>QR Code Sample</title> </head> 

<body> 
	<h1>QR Code Sample</h1> 
	<div> 
		<input id="content" type="text" name="content"/> 
		<input type="button" id="execute" value="QR코드생성"/> 
		<img id="img" style="display:none" onload="this.style.display='block'"/> </div> 
</body> 
</html>

