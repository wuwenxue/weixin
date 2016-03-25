<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关键字列表</title>
<%@include file="/WEB-INF/jsp/share/linkjs.jsp"%> 
<script type="text/javascript">

function updateDialogDoFun(){
	$.messager.progress();
	$('#replyForm').form('submit', {
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
	        }else if(data=="errorPlat"){
	        	$.messager.show({
	        		title:'提示',
	        		msg:'平台错误，请重新进入',
	        		timeout:2000,
	        		showType:'slide'
	        	});
	        }else{
	        	$.messager.show({
	        		title:'提示',
	        		msg:'修改失败',
	        		timeout:2000,
	        		showType:'slide'
	        	});
	        }
	    }
	});
}

var st = "";
function inUrlFun(){
	var selectVal = $("#urlSelect").val();
	var _val = $("#"+st).val();
	$("#"+st).val(_val+selectVal);
}
function onst(id){
	st = id;
}
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<form id="replyForm" action="indexReplyDo.html" method="post" >
		<div data-options="region:'center'" style="padding:10px;">
			<dl class="form_dl clearfix" style="padding: 0px;border: 0px;">
			<input type="hidden" name="plat" value="${plat }">
                <table width="100%">
                	<tr>
                		<td width="50%">
                			<dt>普通用户卷首语：</dt>
			                <dd>
			                	<textarea id="tt1" rows="12" cols="50" name="preface" onclick="onst(this.id)">${preface}</textarea>
			                </dd>
                		</td>
                		<td width="50%">
                			<dt>关注提示语：</dt>
			                <dd>
			                	<textarea id="tt4" rows="12" cols="50" name="keep" onclick="onst(this.id)">${keep}</textarea>
			                </dd> 
                		</td>
                	</tr>
                	
                	<tr>
                		
                	</tr>
                	
                	<tr>
                		<td>
                			<dt>插入链接：</dt>
			                <dd>
								<select id="urlSelect" name="" >
										<c:forEach items="${urlmap}" var="v">
											<option value="${v.key }">${v.value[0] }</option>
										</c:forEach>
									</select>
								<input type="button" value="插入" onclick="inUrlFun()" />
			                </dd>
                		</td>
                		<td align="center">
                			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="updateDialogDoFun()">修改</a>
                		</td>
                	</tr>
                
                </table>
                
			</dl>
			
		</div>
		
		</form>
	</div>
</body>
</html>