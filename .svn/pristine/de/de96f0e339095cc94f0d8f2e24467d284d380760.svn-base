<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
    <meta name="keywords" content="jquery,ui,easy,easyui,web">  
    <meta name="description" content="easyui help you build your web page easily!">  
    <title>微信菜单设置</title>  
<%@include file="/WEB-INF/jsp/share/linkjs.jsp"%> 
<script type="text/javascript">
var parentId,dataGrid;
	$(function(){
		
		dataGrid = $("#dg").datagrid({
			onClickRow: function (rowIndex, rowData) {
                $(this).datagrid('unselectRow', rowIndex);
//                if(rowData.type==0&&addFlag!=""){
//                    $('#dg').datagrid('insertRow',{
//                    	index: rowIndex+1,	// 索引从0开始
//                    	row: {
//                    		sort:0,
//                    		type:1,
//                    		name:'新菜单名称',
//                    		urlorkey:'触发关键词或链接地址'
//                    	}
//                    });
//                }
            }
            
			
		
		});
		
	 });

	//顺序
	function MenuSortFormatter(val,row){
		return '<input type="text" name="sort" value="'+val+'"/><input type="hidden" value="'+row.id+'" name="id" ><input type="hidden" value="'+row.parentid+'" name="parentid" >';  
	}
	
	//类型
	function MenuTypeFormatter(val,row){  
		if(val==0){
			val="<input type='hidden' value='"+val+"' name='type'>"+"一级菜单";
		}else{
			val="<input type='hidden' value='"+val+"' name='type'>"+'<img src="images/bg_repno.gif" style="height:15px;width:40px;"/>'+"二级菜单";
		}
		return val;  
	}
	
	//名称
	function MenuNameFormatter(val,row){ 
		var htmStr;
		if(row.type==1){
			htmStr = '<img src="images/bg_repno.gif" style="height:15px;width:40px;"/><input type="text" name="name" value="'+val+'"/>';
		}else{
			htmStr = '<input type="text" name="name" value="'+val+'"/><a onclick="preAdd('+row.id+');" href="#" style="font-size:large;font-weight:bold;">+</a>';
		}
		return htmStr;  
	}
	
	//关键字或链接
	function MenuKorUFormatter(val,row){
		return '<input type="text" name="urlorkey" value="'+val+'"/> ';  
	}
	
	//删除操作
	function MenuDelFormatter(val,row){
		return '<a onclick="delFun('+val+')" href="#" style="color:blue;">删除</a>';  
	}
	
	//删除菜单
	function delFun(id){
		saveFun("del");//保存数据
		setTimeout(function(){
			$.post('menuDelDo.htm',{id:id}, function(data) {
				$.messager.show({
        			title:'提示',
        			msg:data,
        			timeout:2000,
        			showType:'slide'
        		});
			$.messager.progress('close');
			dataGrid.datagrid("reload");
		},"html");
		}, 100);
	}
	
	function preAdd(id){
		parentId = id;
		addFun(1);
	}
	//添加一级菜单
	function addFun(type){
		saveFun("add");//保存数据
		setTimeout(function(){
			parentId = type==0? 0: parentId;
			$.post('addMenuDo.htm',{type:type,parentId:parentId}, function(data) {
					$.messager.show({
		        		title:'提示',
		        		msg:data,
		        		timeout:2000,
		        		showType:'slide'
		        	});
				$.messager.progress('close');
				dataGrid.datagrid("reload");
			},"html");
		}, 100);
	}
	
</script>  
</head>  
<body>

	<div id="toolbar" style="display: none;">
		<a onclick="addFun(0)" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加一级菜单</a>
		<a onclick="saveFun('save')" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">保存菜单数据</a>
		<a onclick="upsameFun()" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-tip'">同步菜单设置</a>
		<a onclick="revokeFun()" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">撤销菜单设置</a>
	</div>
<!-- 菜单列表  -->
<div class="wp">
	<form id="weixinForm" method="post">
    	<table id="dg" singleSelect="false" fitColumns="true"  toolbar="#toolbar" url="weixinmenuList.htm">
        	<thead>
            	<tr>
                    <th field="sort" width="20" data-options="formatter:MenuSortFormatter">显示顺序</th>
                    <th field="type" width="20" data-options="formatter:MenuTypeFormatter" >菜单类型</th>
                    <th field="name" width="20" data-options="formatter:MenuNameFormatter" >菜单名称</th>
                   	<th field="urlorkey" width="30" data-options="formatter:MenuKorUFormatter" >触发关键词或链接地址</th>
                   	<th field="id" width="10" data-options="formatter:MenuDelFormatter" >操作</th>
                </tr>
            </thead>
        </table>
    </form>
    
</div>
<script type="text/javascript">
//保存菜单数据
function saveFun(model){
	$('#weixinForm').form('submit',{  
		url: "addMenusDo.htm", 
		success: function(result){  
 			if(model=="save"){
 				if (result==1){
 			    	$('#dg').datagrid('reload');    // reload the user data 
 			    	$.messager.show({   // show error message  
 			            title: '系统信息',  
 			            msg:'<span style="color:blue;">保存成功...</span>'  
 			         });  
 			    } else { 
 			         $.messager.show({   // show error message  
 			            title: '系统信息',  
 			            msg:'<span style="color:red;">保存失败,请稍后重试...</span>'  
 			          });  
 			    } 
 			}
		}  
	});
}

//同步菜单设置
function upsameFun(){
	
}

//撤销菜单设置
function revokeFun(){
	
}
</script>
</body>  
</html>