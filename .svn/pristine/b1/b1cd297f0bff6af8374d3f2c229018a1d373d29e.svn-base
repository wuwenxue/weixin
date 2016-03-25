<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图文管理</title>
<%@include file="/WEB-INF/jsp/share/linkjs.jsp"%> 
	<link rel="stylesheet" href="js/kindeditor/themes/default/default.css" />
	<link rel="stylesheet"  href="js/kindeditor/plugins/code/prettify.css" />
<style type="text/css">
body, ul, li, h1, h2, h3, h4, h5, h6, p, form, dl, dt, dd {
	margin: 0px;
	padding: 0px;
	font-family: "Microsoft YaHei", Helvetica, Verdana, Arial, Tahoma;
	font-size: 13px;
	line-height: 1.5em;
	font-weight: normal;
}
ul {
	list-style: none;
}
img {
	border-style: none;
}
.left {
	float: left
}
.right {
	float: right
}
.dib {
	display: inline-block;
}
.vm {
	vertical-align: middle;
}
.block {
	display: block;
}
.tc {
	text-align: center;
}
.tr {
	text-align: right;
}
body {
	background: #fdfdfd;
}
a, a:hover {
	color: #000;
	text-decoration: none
}
.btn {
	padding: 5px 10px 3px;
}
.btn_1 {
	width: 100px;
	height: 30px;
	line-height: 24px;
	text-align: center;
	margin: 0 10px;
}
.nav {
	width: 800px;
}
.nav li {
	line-height: 36px;
	font-size: 15px;
	margin: 0 20px;
	float: left;
}
.nav li a {
	color: #fff;
}
.nav li:hover {
	font-weight: bold
}
/*表格样式*/
.add {
	float: right;
	padding: 5px 10px 3px;
	margin: 0 20px 5px 0;
}
.table {
	border-top: 1px solid #ddd;
	border-left: 1px solid #ddd;
}
.table_header {
	background: #eee;
	font-size: 15px;
	color: #666;
	font-weight: bold;
}
.table td {
	border-bottom: 1px solid #ddd;
	border-right: 1px solid #ddd;
	height: 30px;
	line-height: 30px;
}
.table a {
	color: #000;
	text-decoration: none;
}
.table a:hover {
	color: #f00;
	text-decoration: none;
}
.table tr:hover {
	background: #fefefe
}

/* 图文消息 */
.container {
	width: 998px;
	margin: 0 auto 10px;
}
.container .containerBox {
	margin-top: 20px;
	box-shadow: 0 3px 3px #ddd;
	-moz-box-shadow: 0 3px 3px #ddd;
	-webkit-box-shadow: 0 3px 3px #ddd;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	background-color: #FFF;
	border: 1px solid #d3d3d3
}
.container .containerBox .boxHeader {
	background-color: #f0f0f0;
	background-image: linear-gradient(bottom, #f0f0f0 3%, #f9f9f9 97%, #fff 100%);
	background-image: -moz-linear-gradient(bottom, #f0f0f0 3%, #f9f9f9 97%, #fff 100%);
	background-image: -webkit-linear-gradient(bottom, #f0f0f0 3%, #f9f9f9 97%, #fff 100%);
	height: 35px;
	line-height: 35px;
	padding-left: 20px;
	border-bottom: 1px solid #d3d3d3
}
.container .containerBox .boxHeader h2 {
	font-size: 14px;
	line-height: 35px
}
.container .containerBox .content {
	width: 956px;
	padding: 20px;
	background: #fff;
	overflow: hidden
}
.msg-item-wrapper {
	width: 348px;
	margin-bottom: 26px;
	border: 1px solid #b8b8b8;
	background-color: #f4f4f4;
	box-shadow: 0 2px 2px rgba(0,0,0,0.1);
	-webkit-box-shadow: 0 2px 2px rgba(0,0,0,0.1);
	-moz-box-shadow: 0 2px 2px rgba(0,0,0,0.1);
	border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px
}
.msg-item-wrapper li {
	padding: 10px 15px;
	border-bottom: 1px solid #c6c6c6;
	overflow: hidden;
	cursor:hand;

}
.msg-item-wrapper li:hover {
	background: #eee	
}
.msg-edu {
	margin: 33px 90px 0;
}
.msg-edu a:hover {
	color: #f00
}
.thumbnail {
	width: 70px;
	height: 70px;
	font-size: 16px;
	color: #c0c0c0;
	line-height: 70px;
	border: 1px solid #b2b2b2;
	text-shadow: 0px 1px 0px #fff;
}
.thumbnailImg {
	width: 70px;
	height: 70px;
	display: none;
}
.add-btn {
	height: 72px;
	line-height: 70px;
	color: #222;
	border: 2px dotted #b8b8b8;
	border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
}
.add-btn:hover {
	background: #fff
}
.sub-add-icon {
	width: 18px;
	height: 18px;
	margin-right: 5px;
	background: transparent url(../images/ico.png) no-repeat;
	overflow: hidden
}
.default-tip {
	height: 160px;
	line-height: 160px;
	font-size: 20px;
	text-align: center;
	background: #ececec;
	text-shadow: 0px 1px 0px #fff;
	overflow: hidden;
}
.msg-t {
	height: 28px;
	padding: 0 10px;
	line-height: 28px;
	overflow: hidden;
	margin: 0px;
	color: #FFF;
	background: #333
}
.msg-editer {
	width: 400px;
	background-color: #f8f8f8;
	border: 1px solid #b8b8b8;
	border-radius: 5px 5px 5px 5px;
	box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
	padding: 14px 0px 20px 20px;
	margin-bottom: 10px;
}
.msg-editer .msg-editer-text {
	margin: 10px 0 5px;
}
.msg-input, .cover-area, .msg-txta {
	background: #fff;
	border: 1px solid #d3d3d3;
	color: #666;
	padding: 5px 8px;
	width: 350px;
}
.msg_ok{
	width:100px;
	height:30px;
	background:#5ba607;
	line-height:30px;
	border:1px solid #3d810c;
	margin-right:230px;
	margin-top:20px;
	color:#fff
}

</style>
<script charset="utf-8" src="js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="js/kindeditor/lang/zh_CN.js"></script>
    <script type="text/javascript">
    jQuery.extend({
        handleError: function( s, xhr, status, e ) {
            // If a local callback was specified, fire it
            if ( s.error )
                s.error( xhr, status, e );
            // If we have some XML response text (e.g. from an AJAX call) then log it in the console
            else if(xhr.responseText)
                console.log(xhr.responseText);
        }
    });
    

    function ajaxFileUpload(index)
    {
        
        $("#loading")
        .ajaxStart(function(){
        	$.messager.progress();
        })//开始上传文件时显示一个图片
        .ajaxComplete(function(){
        	$.messager.progress('close');
        });//文件上传完成将图片隐藏起来
        
        $.ajaxFileUpload
        (
            {
                url:'materialAjaxImage.html?flag=materialPath'+index,//用于文件上传的服务器端请求地址
                secureuri:false,//一般设置为false
                fileElementId:'materialPath'+index,//文件上传空间的id属性  <input type="file" id="file" name="file" />
                dataType: 'json',//返回值类型 一般设置为json
                success: function (data, status)  //服务器成功响应处理函数
                {
                	
                	if(data.status=="exterror"){
                		$.messager.alert('提示','请上传正确的图片格式!','info');
                	}else if(data.status=="success"){
                		$("#pic"+index).attr("src",data.message);
        				$("#materialPic"+index).val(data.message);
                	}else{
                		$.messager.alert('提示','请上传图片异常!','error');
                	}
                	
                  
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                	$.messager.alert('提示','请上传图片异常!','error');
                }
            }
        );
    }
    
    <c:forEach items="${maList }" var="v" varStatus="s">
	var editor${v.num} = null;
	</c:forEach>

    function submitForm(){
    	
    	
    	var num = $("#num").val();
    	for(var i=1;i<=num;i++){
    		var _val = $("#validatetitle"+i).val();
    		if($.trim(_val)==""){
    			alert("标题"+i+"不能为空");
    			return;
    		}
    	};
    	$.messager.progress();
    	<c:forEach items="${maList }" var="v" varStatus="s">
 		editor${v.num}.sync();
 		</c:forEach>
    	$('#form').form('submit', {
    		onSubmit: function(){
    			var isValid = $(this).form('validate');
    			if (!isValid){
    				$.messager.progress('close');
    			}
    			return isValid;
    		},
    	    success:function(data){
    	    	$.messager.progress('close');	    	
    	        if(data=="success"){
    	        	$.messager.show({
    	        		title:'提示',
    	        		msg:'修改成功',
    	        		timeout:2000,
    	        		showType:'slide'
    	        	});
    	        }else{
    	        	$.messager.show({
    	        		title:'提示',
    	        		msg:'修改成功',
    	        		timeout:2000,
    	        		showType:'slide'
    	        	});
    	        }
    	        var plat ="";
    	        if($('#plat').val()=="jiayou_web"){
    	        	plat="jiayou";
    	        }else if($('#plat').val()=="tikibox_web"){
    	        	plat="tikibox";
    	        }else{
    	        	plat = "";
    	        }
    	        window.location = "materialList.html?plat="+plat;

    	    }
    	});
    }
    
    function numFun(){
    	var num = $("#num").val();
    	$("#formNum").val(num);
    	
    	for(var i=1;i<=num;i++){
    		$("#myDiv"+i).show();
    	};
    	var _rtnum = parseInt(num)+1;
    	for(var i=_rtnum;i<=7;i++){
    		$("#myDiv"+i).hide();
    	};
    }
    function inUrlFun(index){
    	var selectVal = $("#urlSelect"+index).val();
    	$("#source"+index).val(selectVal);
	}
    
    
    
    </script>
   
</head>
<body>
	
	<div class="easyui-layout" data-options="fit:true">
	<input type="hidden" id="plat" value="${plat }">
            <div data-options="region:'center',border:false" style="padding:10px;">
            	<div style="padding-bottom: 10px;">图文信息数:
            	<select  id="num" onchange="numFun()">
            		<option <c:if test="${num==1 }">selected="selected"</c:if> >1</option>
            		<option <c:if test="${num==2 }">selected="selected"</c:if> >2</option>
            		<option <c:if test="${num==3 }">selected="selected"</c:if> >3</option>
            		<option <c:if test="${num==4 }">selected="selected"</c:if> >4</option>
            		<option <c:if test="${num==5 }">selected="selected"</c:if> >5</option>
            		<option <c:if test="${num==6 }">selected="selected"</c:if> >6</option>
            		<option <c:if test="${num==7 }">selected="selected"</c:if> >7</option>
            	</select>
            	<button  class="msg_ok tc" onclick="submitForm()">完 成</button>
            	</div>
            	
               <div class="content" style="width: 1100px;">
				<form id="form" action="ajaxMaterialUpdateDo.html" method="post" >
				<input type="hidden" name="id" value="${material.id }" />
				<input type="hidden" name="num" id="formNum" value="${num}">
				<c:forEach items="${maList }" var="item" varStatus="s">
					<div id="myDiv${item.num}" class="<c:if test="${item.num%2!=0}">left</c:if><c:if test="${item.num%2==0}">right</c:if> msg-editer" >
					<label class="block msg-editer-text" for="">标题${item.num}</label> 
					<input id="validatetitle${item.num}" value="${item.title }" name="title${item.num}" class="msg-input" type="text" /> 
						<label class="block msg-editer-text" for="">封面${item.num}</label>
					
					<div style="width: 350px">
						<input type="hidden" name="materialPic${item.num}" id="materialPic${item.num}" value="${item.materialPath}" />
						<input style="width: 150px;" type="file" id="materialPath${item.num}" name="materialPath${item.num}" />
						<input type="button" value="上 传" onclick="ajaxFileUpload(${item.num})" />
						<span class="right" >
							<img id="pic${item.num}" alt="" <c:if test="${item.materialPath==null||item.materialPath==''}">src="images/nologo.jpg"</c:if>
					<c:if test="${item.materialPath!=null&&item.materialPath!=''}">src="${item.materialPath}"</c:if> width="90px" height="70px" />
						</span>
					</div>
					
					<label id="noteTitleId" class="block msg-editer-text" for="">概要${item.num}</label>
					<textarea  style="width:90%;height:100px;" name="note${item.num}">${item.note }</textarea>
					
					<label class="block msg-editer-text" for="">跳转链接${item.num}</label> 
					<input value="${item.source }" class="msg-input" type="text" name="source${item.num}" id="source${item.num}"  />
					
					<label class="block msg-editer-text" for="">链接插入</label> 
					<select id="urlSelect${item.num}" >
							<c:forEach items="${urlmap}" var="v">
								<option value="${v.key }">${v.value[0] }</option>
							</c:forEach>
						</select>
					<input type="button" value="插入" onclick="inUrlFun(${item.num})" />
					
					<label class="block msg-editer-text" for="">图文${item.num}</label>
					<textarea name="contentDesc${item.num}" style="width:90%;height:300px;">${item.contentDesc }</textarea>
					<script type="text/javascript">
					
					    $(function(){
					    	editor${item.num} = KindEditor.create('textarea[name="contentDesc${item.num}"]', {
								resizeType : 1,
								items:['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',  'removeformat', '|',
								       'lineheight','source','|','justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',  'insertunorderedlist', '|',
							              'emoticons', 'image', 'multiimage','link'
								],
								uploadJson : 'plugins/kindeditor/jsp/upload_json.jsp'
							});
					    });
					</script>
				</div>
				</c:forEach>
				
				<c:forEach begin="${num+1}" end="7" var="v" varStatus="s">
					<div style="display: none;" id="myDiv${v}" class="<c:if test="${s.current%2!=0}">left</c:if><c:if test="${s.current%2==0}">right</c:if> msg-editer" >
					<label class="block msg-editer-text" for="">标题${s.current}</label> 
					<input id="validatetitle${v}" name="title${s.current}" class="msg-input" type="text" /> 
						<label class="block msg-editer-text" for="">封面${s.current}</label>
					
					<div style="width: 350px">
						<input type="hidden" name="materialPic${s.current}" id="materialPic${s.current}" />
						<input style="width: 150px;" type="file" id="materialPath${s.current}" name="materialPath${s.current}" />
						<input type="button" value="上 传" onclick="ajaxFileUpload(${s.current})" />
						<span class="right" >
							<img id="pic${s.current}" alt="" src="images/nologo.jpg" width="90px" height="70px" />
						</span>
					</div>
						
					<label id="noteTitleId" class="block msg-editer-text" for="">概要${s.current}</label>
					<textarea  style="width:90%;height:100px;" name="note${s.current}"></textarea>
					
					<label class="block msg-editer-text" for="">跳转链接${s.current}</label> 
					<input class="msg-input" type="text" name="source${s.current}" id="source${s.current}"  />
					
					<label class="block msg-editer-text" for="">链接插入</label> 
					<select id="urlSelect${s.current}" >
							<c:forEach items="${urlmap}" var="v">
								<option value="${v.key }">${v.value[0] }</option>
							</c:forEach>
						</select>
					<input type="button" value="插入" onclick="inUrlFun(${s.current})" />
					
					<label class="block msg-editer-text" for="">图文${s.current}</label>
					<textarea name="contentDesc${s.current}" style="width:90%;height:300px;"></textarea>
					<script type="text/javascript">
					
					    $(function(){
					    	editor${s.current} = KindEditor.create('textarea[name="contentDesc${s.current}"]', {
								resizeType : 1,
								items:['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',  'removeformat', '|',
							              'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',  'insertunorderedlist', '|',
							              'emoticons', 'image', 'multiimage','link'
								],
								uploadJson : 'kindeditorUpload.html'
							});
					    });
					</script>
				</div>
				</c:forEach>
				
				
				</form>
			</div>
            </div>
            
        </div>

</body>
</html>