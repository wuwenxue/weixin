<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	// 获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是 http://localhost:8080/MyApp/）:    
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">

function updateDoFun(){
	$.messager.progress();
	$('#myform').form('submit', {
		onSubmit: function(){
			var isValid = $(this).form('validate');
			if (!isValid){
				$.messager.progress('close');
			}
			return isValid;
		},
	    success:function(data){
	    	var retobj = jQuery.parseJSON(data);
	    	$.messager.progress('close');	    	
	    	 if(retobj.status==0){
	        	$.messager.show({
	        		title:'提示',
	        		msg:'修改成功',
	        		timeout:2000,
	        		showType:'slide'
	        	});
	        	$('#dialog').window('close');
	        }else{
	        	$.messager.show({
	        		title:'提示',
	        		msg:'修改失败',
	        		timeout:2000,
	        		showType:'slide'
	        	});
	        }
	        $("#myform").find("input").val("");
	        dataGrid.datagrid("reload");

	    }
	});
}



</script>
<div class="easyui-layout" data-options="fit:true">
	<form id="myform" action="card/updateDo.html" method="post" >
		<div data-options="region:'center'" style="padding:10px;">
			<input type="hidden" name="id" value="${obj.id}" />
			<dl class="form_dl clearfix">
				<dt>姓名：</dt>
                <dd>
                	<input class="easyui-validatebox" value="${obj.name}" name="name" data-options="required:true"  >
                </dd>
                
               <dt>类型：</dt>
                <dd>
                	<select name="type">
                		<option value="0" <c:if test="${obj.type==0}">selected="selected"</c:if> >全场工作证</option>
                		<option value="1" <c:if test="${obj.type==1}">selected="selected"</c:if> >内场工作证 </option>
                		<option value="2" <c:if test="${obj.type==2}">selected="selected"</c:if> >现场销售证</option>
                	</select>
                </dd>
                
			</dl>
			
		</div>
		
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="updateDoFun()">保存</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#dialog').window('close');">取消</a>
		</div>
		</form>
	</div>
