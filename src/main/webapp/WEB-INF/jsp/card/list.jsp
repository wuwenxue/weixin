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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<%@include file="/WEB-INF/jsp/share/linkjs.jsp"%> 


    <script type="text/javascript">

    var dataGrid;
    $(function() {
    	
    	dataGrid = $('#dataGrid').datagrid({
    		url : 'card/listDo.html',
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
    			title : '姓名',
    			width :10
    		},{
    			field : 'typeDesc',
    			title : '类型',
    			width : 10
    		},{
    			field : 'head',
    			title : '头像',
    			width : 10,
    			formatter:function(v){return '<img width="100" height="100" src="'+v+'" />';}
    		}
    		
    		] ]
    		
    	});
    	
    });

   


    function delFun(){
    	var rows = dataGrid.datagrid("getChecked");
    	if(rows.length==0){
    		$.messager.alert('提示','请选择要删除的内容','info');
    	}else{
    		$.messager.confirm('确认', '确定删除吗？', function(r) {
    			if(r){
    				$.messager.progress({
    					title : '提示',
    					text : '数据处理中，请稍后....'
    				});
    				var ids = new Array();
    				for(var i=0;i<rows.length;i++){
    					ids.push(rows[i].id);
    				}
    				var idsStr = ids.join(',');
    				$.post('card/delDo.html',{ids:idsStr}, function(data) {
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
    	$('#dialog').window('open');
    	$('#dialog').window('refresh', "card/add.html");
    }
    
    function updateFun(){
    	var row = dataGrid.datagrid("getSelected");
    	if(row==null){
    		$.messager.alert('提示','请选择要修改的内容','info');
    	}else{
    		var _rid = row.id;
    		$('#dialog').window('open');
        	$('#dialog').window('refresh', "card/update.html?id="+_rid);
    	}
    	
    }
    </script>
    <script type="text/javascript">
	var ws = null;
	function startServer() {
		var url = "ws://127.0.0.1:8080/weixin_server/chat.ws?username=server";
		if ('WebSocket' in window) {
			ws = new WebSocket(url);
		} else if ('MozWebSocket' in window) {
			ws = new MozWebSocket(url);
		} else {
			alert("你的浏览器过时了！都不支持WebSocket！");
			return;
		}

		ws.onopen = function() {
		};
		ws.onmessage = function(event) {
		};
		ws.onclose = function() {
		};
	}

	function markCard() {
		var row = dataGrid.datagrid("getSelected");
    	if(row==null){
    		$.messager.alert('提示','请选择要制证的内容','info');
    	}else{
    		$.messager.confirm('确认', '确定制证吗？', function(r) {
    			if(r){
    			
    				ws.send(row.id);
    			}
    		});
    	}
    	
    	
			
	}
</script>
</head>
<body onload="startServer()">
	<div class="easyui-layout" data-options="fit : true,border : false">	
		
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	
	<div id="toolbar" style="display: none;">
			<a onclick="addFun()" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
			<a onclick="updateFun();" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
			<a onclick="delFun();" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
			<a onclick="markCard();" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">制证</a>
	</div>

	<div id="dialog" class="easyui-window" title="证件管理"style="width: 550px; height: 450px;padding: 5px;"
		data-options="resizable:true,modal:true,shadow:true,collapsible:false,minimizable:false,maximizable:true,closed:true">
		
		<div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center',border:false" style="padding:10px;">
                
            </div>
        </div>
		
	</div>

</body>
</html>