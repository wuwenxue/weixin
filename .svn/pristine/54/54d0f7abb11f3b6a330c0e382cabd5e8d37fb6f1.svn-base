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
		$('#parentImg').hide();
		$("#insertId").show();
	}else if(_parentTypeSelect==1){
		$("#parentMessageTr").hide();
		$("#parentTuwenTr").show();
		$("#nineTuwenTr").hide();
		$("#insertId").hide();
		$('#parentImg').hide();
	}else if(_parentTypeSelect==2){
		$("#parentMessageTr").hide();
		$("#parentTuwenTr").hide();
		$("#nineTuwenTr").show();
		$("#insertId").hide();
		$('#parentImg').hide();
	}else if(_parentTypeSelect==3){
		$("#parentMessageTr").hide();
		$("#parentTuwenTr").hide();
		$("#nineTuwenTr").hide();
		$("#insertId").hide();
		$("#parentImg").show();
	}
}


function inUrlFun(){
	var selectVal = $("#urlSelect").val();
	var _val = $("#comment").val();
	$("#comment").val(_val+selectVal);
}

</script>
<div class="easyui-layout" data-options="fit:true">
	<form id="replyForm" action="replyUpdateDo.html" method="post" enctype="multipart/form-data">
		<div data-options="region:'center'" style="padding:10px;">
			<input type="hidden" name="plat" value="${plat }">
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
                
                <dt>回复用户类型：</dt>
                <dd>
                	<select name="washis"  >
							<option <c:if test="${obj.washis==0 }">selected="selected"</c:if> value="0">普通用户</option>
							<option <c:if test="${obj.washis==2 }">selected="selected"</c:if> value="2">洗车点</option>
						</select>
                </dd>
                <dt>回复类型：</dt>
                <dd>
                	<select name="replyType" id="parentTypeSelect" onchange="parentTypeSelectFun()">
							<option <c:if test="${obj.replyType==0 }">selected="selected"</c:if> value="0">文字</option>
							<option <c:if test="${obj.replyType==1 }">selected="selected"</c:if> value="1">图文</option>
							<option <c:if test="${obj.replyType==2 }">selected="selected"</c:if> value="2">内置图文</option>
							<option <c:if test="${obj.replyType==3 }">selected="selected"</c:if> value="3">图片</option>
						</select>
                </dd>
                
                <div id="parentMessageTr" <c:if test="${obj.replyType!=0 }">style="display: none;"</c:if> >
                <dt>回复内容：</dt>
                <dd>
                	<textarea id="comment" rows="5" cols="50" name="comment">${obj.comment}</textarea>
                </dd>
                </div>
                
                <div id="parentImg" <c:if test="${obj.replyType!=3 }">style="display: none;"</c:if> >
                <dt>图片地址：</dt>
                <dd>
					<input id="file" name="picUrl" type="file" >
					<c:if test="${obj.replyType==3 }">
					</br>
					<img width="150px;" height="120px" src="http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=${accessToken }&media_id=${obj.comment}">
					</c:if>
                </dd>
                </div>
                
                <div id="parentTuwenTr" <c:if test="${obj.replyType!=1 }">style="display: none;"</c:if> >
                <dt>请选择图文：</dt>
                <dd>
                	<select name="materialId" >
							<c:forEach items="${materiallist}" var="v">
								<option <c:if test="${obj.materialId==v.id }">selected="selected"</c:if> value="${v.id }">${v.name }</option>
							</c:forEach>
						</select>
                </dd>
                </div>
                
                <div id="nineTuwenTr" <c:if test="${obj.replyType!=2 }">style="display: none;"</c:if> >
                <dt>请选择系统内置图文：</dt>
                <dd>
                	<select name="inlayId" >
							<c:forEach items="${urlmap2}" var="v">
								<option <c:if test="${obj.inlayId==v.key }">selected="selected"</c:if> value="${v.key}">${v.value }</option>
							</c:forEach>
						</select>
                </dd>
                </div>
                
                <div id="insertId" <c:if test="${obj.replyType!=0 }">style="display: none;"</c:if>>
                <dt>插入链接：</dt>
                <dd>
					<select id="urlSelect" name="" >
							<c:forEach items="${urlmap}" var="v">
								<option value="${v.key }">${v.value[0] }</option>
							</c:forEach>
						</select>
					<input type="button" value="插入" onclick="inUrlFun()" />
                </dd>
                </div>
			</dl>
			
		</div>
		
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="addReplyDialogDoFun()">保存</a> 
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#addDialog').window('close');">取消</a>
		</div>
		</form>
	</div>
