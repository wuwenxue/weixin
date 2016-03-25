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

function addDoFun(){
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
	        	$('#dialog').window('close');
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

$(function() {
	$('#file').uploadify({
		 //开启调试
        'debug' : false,
		'formData'     : {},
		'buttonText' : '选择照片',
		'swf'      : 'js/uploadify/uploadify.swf',
		'uploader' : '<%=basePath%>upload.html',
		'width' : 80,
		'fileTypeExts': '*.gif; *.jpg; *.png',
		'auto': true,
		'fileObjName':'file',
		'onUploadSuccess' : function(file, data, response) {
			var retobj = jQuery.parseJSON(data);
			if(retobj.status==0){
				$("#headId").attr("src",retobj.path);
				$("#path").val(retobj.path);
				$.messager.show({
	        		title:'提示',
	        		msg:'上传成功',
	        		timeout:2000,
	        		showType:'slide'
	        	});
			}else{
				$.messager.show({
	        		title:'提示',
	        		msg:'上传失败',
	        		timeout:2000,
	        		showType:'slide'
	        	});
			}
           
        }
	});
});



</script>
<div class="easyui-layout" data-options="fit:true">
	<form id="myform" action="card/addDo.html" method="post" >
		<div data-options="region:'center'" style="padding:10px;">
			<dl class="form_dl clearfix">
				<dt>姓名：</dt>
                <dd>
                	<input class="easyui-validatebox" name="name" data-options="required:true"  >
                </dd>
                
                <dt>类型：</dt>
                <dd>
                	<select name="type">
                		<option value="0">全场工作证</option>
                		<option value="1">内场工作证 </option>
                		<option value="2">现场销售证</option>
                	</select>
                </dd>
                
            	<dd>
                	<div id="queue"></div>
					<input id="file" name="file" type="file" multiple="true">
					<img id="headId" src="holder.js/100x200">
					<input type="hidden" name="path" id="path" />
                </dd>
               
			</dl>
		</div>
		
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="addDoFun()">保存</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#dialog').window('close');">取消</a>
		</div>
		</form>
	</div>
	
	
<script type="text/javascript" src="js/uploadify/holder.js"></script>
