<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关键字列表</title>
    <%@include file="/WEB-INF/jsp/share/linkjs.jsp"%> 
    <script type="text/javascript">

    var dataGrid;
    var plat = '${plat}';
    $(function() {
    	
    	dataGrid = $('#dataGrid').datagrid({
    		url : 'replylistDo.html?plat=${plat}',
    		fit : true,
    		idField : 'id',
    		fitColumns : true,
    		border : false,
    		pagination : true,
    		pageSize : 50,
    		pageList : [ 10, 20, 30, 40, 50 ],
    		selectOnCheck : true,
    		singleSelect : true,
    		nowrap : true,
    		toolbar: '#toolbar',
    		columns : [ [ {
    			field : 'id',
    			title : '编号',
    			width : 10,
    			checkbox : false
    		},{
    			field : 'name',
    			title : '标题',
    			width :10
    		},{
    			field : 'command',
    			title : '数字指令',
    			width : 10
    		},{
    			field : 'keyworld',
    			title : '关键字',
    			width : 20
    		},{
    			field : 'replyType',
    			title : '回复类型',
    			width : 10,
    			formatter : function(value, row, index) {
    				if(value==0){
    					return "文字";
    				}else if(value==1){
    					return "图文";
    				}else if(value==2){
    					return "内置图文";
    				}else if(value==3){
    					return "图片";
    				}else{
    					return "";
    				}
    			}
    		},{
    			field : 'washis',
    			title : '回复用户类型',
    			width : 10,
    			formatter : function(value, row, index) {
    				if(value==0){
    					return "普通用户";
    				}else if(value==1){
    					return "普通用户";
    				}else if(value==2){
    					return "洗车点";
    				}else{
    					return "";
    				}
    			}
    		},{
    			field : 'comment',
    			title : '回复内容',
    			width : 50,
    			formatter : function(value, row, index) {
    				if(row.replyType==0||row.replyType==3){
    					return row.comment;
    				}else if(row.replyType==1){
    					return row.materialName;
    				}else if(row.replyType==2){
    					return row.materialName;
    				}else{
    					return "";
    				}
    			}
    		}    ] ]
    		
    	});
    	
    });

   


    function delFun(){
    	var rows = dataGrid.datagrid("getSelected");
    	if(rows.length==0){
    		$.messager.alert('提示','请选择要删除的内容','info');
    	}else{
    		$.messager.confirm('确认', '确定删除吗？', function(r) {
    			if(r){
    				$.messager.progress({
    					title : '提示',
    					text : '数据处理中，请稍后....'
    				});
    				$.post('replyDelDo.html',{id:rows.id}, function(data) {
    					$.messager.show({
    		        		title:'提示',
    		        		msg:'删除成功',
    		        		timeout:2000,
    		        		showType:'slide'
    		        	});
    					$.messager.progress('close');
    					dataGrid.datagrid("reload");
    				},"html");
    			}
    		});
    	}
    }
   

    function addFun(){
    	$('#addDialog').window('open');
    	$('#addDialog').window('refresh', "replyAdd.html?plat="+plat);
    }
    
    function updateFun(){
    	var row = dataGrid.datagrid("getSelected");
    	if(row==null){
    		$.messager.alert('提示','请选择要修改的内容','info');
    	}else{
    		var _rid = row.id;
    		$('#addDialog').window('open');
        	$('#addDialog').window('refresh', "replyUpdate.html?id="+_rid+"&plat="+plat);
    	}
    	
    }
    </script>
    
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">	
		
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	
	<div id="toolbar" style="display: none;">
			<a onclick="addFun()" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
			<a onclick="updateFun();" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
			<a onclick="delFun();" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
	</div>

	<div id="addDialog" class="easyui-window" title="添加回复"style="width: 550px; height: 450px;padding: 5px;"
		data-options="resizable:true,modal:true,shadow:true,collapsible:false,minimizable:false,maximizable:true,closed:true">
		
		<div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center',border:false" style="padding:10px;">
                
            </div>
        </div>
		
	</div>

</body>
</html>