<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%    
String path = request.getContextPath();    
// 获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是 http://localhost:8080/MyApp/）:    path
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";    
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="${basePath}css/weixin.css" />
    <script type="text/javascript" src="${basePath}js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${basePath}js/swipe.js"></script>
   

<title>${title }</title>
<meta name="keywords" content="">
<meta name="description" content="">
<script type="text/javascript">
$(function(){
	$(".wp").css('min-height',$(document).height()-75);
	//返回顶部
	$("#backtotop").click(function() {
	$("body,html").animate({
		scrollTop: 0
		},500);
	});
});
</script>

</head>
<div class="wp">
    <div class="cont p10">
        <h2 class="tit">${title }</h2>
        <span class="date">${time}</span>
        <article class="mt20">
           ${content}
         </article>
    </div><!--/cont-->
</div><!--/wp-->
	<footer class="p10">
        <ul class="ft_list tc">
            <li><a class="contact" href="javascript:;">联系我们</a></li>
            <li><a href="weixinabout.htm">回到主页</a></li>
            <li><a id="backtotop" href="javascript:;">返回顶部</a></li>
        </ul><!--/ft_list-->
        <p class="mt15 tc">© 驾友网</p>
    </footer>
	<div style="display:none;"></div>
</body>
</html>