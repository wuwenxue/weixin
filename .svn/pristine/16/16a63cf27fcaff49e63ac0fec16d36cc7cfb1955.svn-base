<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图文管理</title>
  <%@include file="/WEB-INF/jsp/share/linkjs.jsp"%> 

    <script type="text/javascript">
	var plat = '';
    var dataGrid;
    $(function() {
    	plat = '${plat}';
    	dataGrid = $('#dataGrid').datagrid({
    		url : 'materialListDo.html?plat='+plat,
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
    			title : '名称',
    			width :100,
    		},{
    			field : 'createtime',
    			title : '添加时间',
    			width : 50,
    		}    ] ]
    		
    	});
    	
    });

    function imgInfoFun(src){
    	$('#imgDialog').window('open');
    	$("#imgId").attr("src",src);
    }


    function delFun(){
    	var row = dataGrid.datagrid("getSelected");
    	if(row==null){
    		$.messager.alert('提示','请选择要删除的内容','info');
    	}else{
    		$.messager.confirm('确认', '确定删除吗？', function(r) {
    			if(r){
    				$.post('materialdeldo.html',{id:row.id}, function(data) {
    	    			if(data=='success'){
    	    				$.messager.show({
    	    	        		title:'提示',
    	    	        		msg:'删除成功',
    	    	        		timeout:2000,
    	    	        		showType:'slide'
    	    	        	});
    	    			}else{
    	    				$.messager.show({
    	    	        		title:'提示',
    	    	        		msg:'删除失败',
    	    	        		timeout:2000,
    	    	        		showType:'slide'
    	    	        	});
    	    			}
    					
    					$.messager.progress('close');
    					dataGrid.datagrid("reload");
    				},"html");	
    			}
    		});
    	}
    }

    function addFun(){
    	//$('#addDialog').window('maximize');
    	//$('#addDialog').window('open');
    	//$('#addDialog').window('refresh', "materialAdd.htm");
    	window.location = 'materialAdd.html?num=1&plat='+plat;
    }
  
    
    function updateFun(){
    	var row = dataGrid.datagrid("getSelected");
    	if(row==null){
    		$.messager.alert('提示','请选择要修改的内容','info');
    	}else{
    		var _rid = row.id;
    		window.location = "materialupdate.html?id="+_rid+"&plat="+plat;
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



</body>
</html>