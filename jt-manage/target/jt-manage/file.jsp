<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	</br>
	<!-- enctype=“multipart/form-data”:表示多媒体信息 -->
	<form action="http://localhost:8091/file" method="post" enctype="multipart/form-data">
		<input type="file" value="选择" name="file"/>
		</br></br>
		<input type="submit" value="提交">
	</form>
</body>
</html>