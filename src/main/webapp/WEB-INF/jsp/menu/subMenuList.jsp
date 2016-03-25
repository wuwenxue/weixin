<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	
		
			<table id="dg${menuId}" style="height:250px;" >
				
			</table>
		
	
	<div id="toolbar${menuId}" style="display: none;">
			<a onclick="updateService();" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
			<a onclick="delService();" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
	</div>
	
	 
	 <script type="text/javascript">
	 $(function() {
		 $('#dg${menuId}').datagrid({
	    		url : 'subMenuListDo.html?id=${menuId}',
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
	    		toolbar: '#toolbar${menuId}',
	    		columns : [ [
	    			{
	    			field : 'name',
	    			title : '名称',
	    			sortable:true,
	    			width :10
	    		},{
	    			field : 'type',
	    			title : '类型',
	    			sortable:true,
	    			width : 10
	    		},{
	    			field:'keyMenu',
	    			title:'接口推送',
	    			width:10
	    		},{
	    			field:'url',
	    			title:'链接地址',
	    			width:10
	    		},{
	    			field : 'createDate',
	    			title : '添加时间',
	    			sortable:true,
	    			width : 10
	    		}
	    		] ]
	    		
	    	});
	 });
	 
	 function updateService(){
		 var row=$('#dg${menuId}').datagrid('getSelected');
		 if(row){
			 $('#updateMenu').dialog('open').dialog('setTitle', '修改自定义菜单');
			 $('#form').form('clear');
	    		$('#menu_id').val(row.id);
	    		$('#sub_muen').val(row.menuId);
	    		$('#name').val(row.name);
	    		$('#menu_Type').attr("value",row.type);
	    		if(row.type=="view"){
	    			$("#urlAdd").show();
	    			$("#urlAddress").val(row.url);
	    			$('#keyUpdate').hide();
	    		}else{
	    			$('#keyUpdate').show();
	    			$('#keyMenus').val(row.keyMenu);
	    			$("#urlAdd").hide();
	    		}
	    		$('#sub_muen').attr("value",row.menuId);
		 }else{
			 $.messager.alert('提示','请选择要修改的服务项','info');
		 }
		 
	 }
	 function delService(){
		 var row=$('#dg${menuId}').datagrid('getSelected');
		 if(row){
			 $.ajax({
				type:'POST',
				url:'delMenu.html',
				data:{id:row.id},
				success:function(data){
					var result=eval("("+data+")");
					if(result.status==0){
						$('#dg${menuId}').datagrid('reload');
						 $.messager.show({  
	 	                      title: '提示',  
	 	                      msg:'<span style="color:blue;">操作成功</span>'
	 	                  });  
					}else{
						 $.messager.show({  
	 	                      title: '提示',  
	 	                      msg:'<span style="color:red;">操作失败</span>'
	 	                  });  
					}
				}
			 });
			 }else{
				 $.messager.alert('提示','请选择一行','info');
			 }
		 }
    </script>
