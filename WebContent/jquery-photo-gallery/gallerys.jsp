<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>图片查看器</title>
    <link rel="stylesheet" href="photoGallery.css"/>
    <script src="jquery.js"></script>
    <script src="jquery.photo.gallery.js"></script>
</head>
<body>
<div class="box">
	<header drag>
		<div class="winControl" noDrag>
	        <span class="closeWin" title="关闭">
	        <i class="icon_close-big"></i>
	        </span>
	    </div>
	</header>
	<div class="gallery"></div>
</div>
<script>
	$.initGallery();
</script>
</body>
</html>