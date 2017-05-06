<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!DOCTYPE html> <html> <head> <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 

<!-- 스프링 context이용해서 아름답게 짜는방법이 있음 -->


<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.7.2.min.js'/>" charset="UTF-8"></script> 

<script type="text/javascript"> 
	$(document).ready(function(){
		
	
		 $("#execute").click(function(){  
				
				$.ajax({
						url : "/ios.do",
						dataType : "json",
						type : "get",
						data :{ "loginKey" : $("#content").val() },
						success : function(data) {
							//alert("QrUrl is :" + data.qrUrl);
							//$("#img").attr("src", data.qrUrl);
							alert(data.data);
							$("#getData").val(data.data);
						},
						error : function(request, status, error) {
							alert("code:" + request.status + "\n" + "error:" + error);
						}
					});
			});  
	});

</script> 

<title>ACCESS TEST</title> </head> 

<body> 
	<h1>HELLO KSL </h1> 
	<div> 
		<input id="content" type="text" name="content"/> 
		<input type="button" id="execute" value="helloServer"/> 
		<input id="getData" type="text" name="datas"/> 

</body> 
</html>

