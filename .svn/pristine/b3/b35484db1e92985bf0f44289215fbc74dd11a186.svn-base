<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">

function addReplyDialogDoFun(){
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
	        	$('#addDialog').window('close');
	        }else{
	        	$.messager.show({
	        		title:'提示',
	        		msg:'修改失败',
	        		timeout:2000,
	        		showType:'slide'
	        	});
	        }
	        $("#form").find("input").val("");
	        dataGrid.datagrid("reload");

	    }
	});
}

function parentTypeSelectFun(){
	var _parentTypeSelect = $("#parentTypeSelect").val();
	if(_parentTypeSelect==0){
		$("#parentMessageTr").show();
		$("#parentTuwenTr").hide();
		$("#nineTuwenTr").hide();
		$("#insertId").show();
	}else if(_parentTypeSelect==1){
		$("#parentMessageTr").hide();
		$("#parentTuwenTr").show();
		$("#nineTuwenTr").hide();
		$("#insertId").hide();
	}else if(_parentTypeSelect==2){
		$("#parentMessageTr").hide();
		$("#parentTuwenTr").hide();
		$("#nineTuwenTr").show();
		$("#insertId").hide();
	}
}


function inUrlFun(){
	var selectVal = $("#urlSelect").val();
	var _val = $("#comment").val();
	$("#comment").val(_val+selectVal);
}

</script>
<div class="easyui-layout" data-options="fit:true">
	<form id="replyForm" action="user/updateDo.html" method="post" >
		<div data-options="region:'center'" style="padding:10px;">
			<input type="hidden" name="id" value="${obj.id}" />
			<dl class="form_dl clearfix">
				<dt>标题：</dt>
                <dd>
                	<input class="easyui-validatebox" value="${obj.name}" name="name" data-options="required:true"  >
                </dd>
                
                <dt>数字指令：</dt>
                <dd>
                	<input name="command" value="${obj.command}" class="easyui-numberbox" >
                </dd>
                
                <dt>关键字：</dt>
                <dd>
                	<input name="keyworld" value="${obj.keyworld}"  />
                </dd>
                
			</dl>
			
		</div>
		
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="addReplyDialogDoFun()">保存</a> 
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#addDialog').window('close');">取消</a>
		</div>
		</form>
	</div>
