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

function addReplyDialogDoFun(){
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
	        		msg:'添加成功',
	        		timeout:2000,
	        		showType:'slide'
	        	});
	        	$('#addDialog').window('close');
	        }else{
	        	$.messager.show({
	        		title:'提示',
	        		msg:retobj.desc,
	        		timeout:2000,
	        		showType:'slide'
	        	});
	        }
	        dataGrid.datagrid("reload");

	    }
	});
}


</script>
<div class="easyui-layout" data-options="fit:true">
	<form id="myform" action="user/addDo.html" method="post" >
		<div data-options="region:'center'" style="padding:10px;">
			<dl class="form_dl clearfix">
				<dt>标题：</dt>
                <dd>
                	<input class="easyui-validatebox" name="name" data-options="required:true"  >
                </dd>
                
                <dt>数字指令：</dt>
                <dd>
                	<input name="command" class="easyui-numberbox" >
                </dd>
                
                <dt>关键字：</dt>
                <dd>
                	<input name="keyworld"   />
                </dd>
                
               
			</dl>
		</div>
		
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="addReplyDialogDoFun()">保存</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#addDialog').window('close');">取消</a>
		</div>
		</form>
	</div>
