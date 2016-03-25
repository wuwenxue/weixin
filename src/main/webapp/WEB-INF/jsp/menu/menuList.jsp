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
    var plat = "";
    var dataGrid;
    $(function() {
    	plat = '${plat}';
    	dataGrid = $('#dataGrid').datagrid({
    		url : 'menuListDo.html?plat='+plat,
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
    		toolbar:"#toolbar",
    		columns : [ [ {
    			field : 'name',
    			title : '名称',
    			width :10
    		},{
    			field : 'type',
    			title : '类型',
    			width :10
    		},{
    			field : 'keyMenu',
    			title : '接口推送',
    			width :10
    		},{
    			field : 'url',
    			title : '链接地址',
    			width :10
    		},{
    			field : 'createDate',
    			title : '加入时间',
    			width :10
    		}
    		
    		] ],
			view : detailview,
			detailFormatter : function(index, row) {
				return '<div id="ddv-' + index + '" style="padding:5px 0"></div>';
			},
			onExpandRow : function(index, row) {
				$('#ddv-' + index).panel(
						{
							height : 260,
							border : false,
							cache : false,
							href : 'subMenuList.html?id='+ row.id+'&plat='+plat,
							onLoad : function() {
								$('#dataGrid').datagrid(
										'fixDetailRowHeight',
										index);
							}
						});
				$('#dataGrid').datagrid('fixDetailRowHeight',
						index);
			}
    		
    	});
    });

   


    function delFun(){
    	var rows = dataGrid.datagrid("getSelected");
    	if(rows==null){
    		$.messager.alert('提示','请选择要删除的内容','info');
    	}else{
    		$.messager.confirm('确认', '确定删除吗？', function(r) {
    			if(r){
    				$.messager.progress({
    					title : '提示',
    					text : '数据处理中，请稍后....'
    				});
    				$.post('delMenu.html',{id:rows.id}, function(data) {
    					var obj = eval("("+data+")");
    					if(obj.status==0){
    						$.messager.progress('close');
    						alert('操作成功');
    						window.location="menuList.html?plat="+plat;
    					}else{
    						$.messager.show({
        		        		title:'提示',
        		        		msg:'删除失败',
        		        		timeout:2000,
        		        		showType:'slide'
        		        	});
    					}
    					
    				},"html");
    			}
    		});
    	}
    }
   

    function addFun(){
    		var count = dataGrid.datagrid("getData").total;
    		var rows = dataGrid.datagrid("getSelected");
    		if(count==0){
    			$('#menuDiv').hide();
    		}else{
    			$('#menuDiv').show();
    		}
    		$('#urlDiv').hide();
    		$('#saveMenu').dialog('open').dialog('setTitle', '添加自定义菜单');
    		$("#myform").form('clear');
    		$('#plat').val(plat);
    		if(rows){
    			$('#submuen').attr("value",rows.id);
    		}else{
    			$('#submuen').attr("value","0");
    		}
    }
    
    function addDoFun(){
    	$('#myform').form('submit',{
    		url:'saveMenu.html',
    		onSubmit : function() {
				return $(this).form('validate');
			},
			success:function(result){
				var res=eval("("+result+")");
				if(res.status==0){
					$('#saveMenu').dialog('close');
					alert('操作成功');
					window.location="menuList.html?plat="+plat;
				}else{
					$.messager.alert("提示", res.desc, "info");
				}
			}
    		
    	});
    }
    
    function urlDivShow(val){
    	if(val=="view"){
    		$('#urlDiv').show();
    		$('#keyAddDiv').hide();
    	}else{
    		$('#urlDiv').hide();
    		$('#keyAddDiv').show();
    	}
    }
    
    function updateFun(){
    	var rows = dataGrid.datagrid("getSelected");
    	if(rows==null){
    		$.messager.alert('提示','请选择要修改的内容','info');
    	}else{
    		$('#updateMenu').dialog('open').dialog('setTitle', '修改自定义菜单');
    		$('#form').form('clear');
    		$('#menu_id').val(rows.id);
    		$('#name').val(rows.name);
    		$('#menu_plat').val(plat);
    		$('#sub_muen').attr("value",rows.menuId);
    		$('#menu_Type').attr("value",rows.type);
    		
    		if(rows.type=="view"){
    			$("#urlAdd").show();
    			$("#urlAddress").val(rows.url);
    			$('#keyUpdate').hide();
    		}else{
    			$('#keyUpdate').show();
    			$('#keyMenus').val(rows.keyMenu);
    			$("#urlAdd").hide();
    		}
    	}
    	
    }
    
    function updateDoFun(){
    	if($('#menu_Type').val()=="view"){
    		$('#keyMenus').val('');
    	}else{
    		$('#urlAddress').val('');
    	}
    	$('#updateform').form('submit',{
    		url:'updateMenu.html',
    		onSubmit : function() {
				return $(this).form('validate');
			},
			success:function(result){
				var res=eval("("+result+")");
				if(res.status==0){
					$('#updateMenu').dialog('close');
					alert('操作成功');
					window.location="menuList.html?plat="+plat;
				}else{
					$.messager.show({  
	                      title: '提示',  
	                      msg:'<span style="color:red;">'+res.desc+'</span>'
	                  });
				}
			}
    		
    	});
    }
    
    function urlAddShow(val){
    	if(val=="view"){
    		$('#urlAdd').show();
    		$('#keyUpdate').hide();
    	}else{
    		$('#urlAdd').hide();
    		$('#keyUpdate').show();
    	}
    }
    
    function updateWeixinFun(){
    	$.post("syncMenu.html?plat="+plat,function(obj){
    		if(obj=="0"){
    			$.messager.alert("提示", "同步成功", "info");
    		}else if(obj=="1"){
    			$.messager.alert("提示", "同步失败", "info");
    		}else{
    			$.messager.alert("提示","平台未知","info");
    		}
    	},"json");
    }
    </script>
</head>
<body onload="">
	<div class="easyui-layout" data-options="fit : true,border : false">	
		
		<div data-options="region:'center',border:false">
			<table id="dataGrid">
			
			</table>
		</div>
	</div>
	
	<div id="toolbar" style="display: none;">
			<a onclick="addFun()" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
			<a onclick="updateFun();" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
			<a onclick="delFun();" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
			<a onclick="updateWeixinFun()" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">同步微信菜单</a>
	</div>

<!-- 添加菜单 -->
<div id="saveMenu" class="easyui-window"
		data-options="top:30,resizable:false,closed:true,collapsible:false,minimizable:false,maximizable:false,modal:true"
		style="width: 550px; height: auto; padding: 5px;">
	<form id="myform" method="post">
			<dl class="form_dl clearfix">
				<input type="hidden" id="plat" name="plat">
				<dt>名称：</dt>
                <dd>
                	<input class="easyui-validatebox" name="name" data-options="required:true"  >
                </dd>
                
                <div id="menuDiv">
                <dt>父菜单：</dt>
                <dd>
                	<select name="menuId" id="submuen" >
                		<option value="0">请选择</option>
                		<c:forEach items="${menuIdList }" var="list">
                			<option value="${list.id }">${list.name }</option>
                		</c:forEach>
                	</select>
                </dd>
                </div>
                
                <dt>菜单类型：</dt>
                <dd>
                	<select name="type" id="menuType" onchange="urlDivShow(this.value);">
                		<option value="click">click</option>
                		<option value="view">view</option>
                		<option value="scancode_push">scancode_push</option>
                		<option value="scancode_waitmsg">scancode_waitmsg</option>
                		<option value="pic_sysphoto">pic_sysphoto</option>
                		<option value="pic_photo_or_album">pic_photo_or_album</option>
                		<option value="pic_weixin">pic_weixin</option>
                		<option value="location_select">location_select</option>
                	</select>
                </dd>
                
                <div id="urlDiv" style="display: none;">
                <dt>URL地址：</dt>
                <dd>
                	<input name="urlAddress" value=""  >
                </dd>
                </div>
                
                <div id="keyAddDiv">
                <dt>KEY：</dt>
                <dd>
                	<input name="keyMenus" value=""  >
                </dd>
                </div>
               
			</dl>
		
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="addDoFun()">保存</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#saveMenu').window('close');">取消</a>
		</div>
		</form>
	</div>

<!-- 修改菜单 -->
<div id="updateMenu" class="easyui-window"
		data-options="top:30,resizable:false,closed:true,collapsible:false,minimizable:false,maximizable:false,modal:true"
		style="width: 550px; height: auto; padding: 5px;">
	<form id="updateform" method="post">
			<input type="hidden" name="id" id="menu_id">
			<input type="hidden" id="menu_plat" name="plat">
			<dl class="form_dl clearfix">
				<dt>名称：</dt>
                <dd>
                	<input class="easyui-validatebox" name="name" id="name" data-options="required:true"  >
                </dd>
                
                <div id="menuDiv">
                <dt>父菜单：</dt>
                <dd>
                	<select name="menuId" id="sub_muen" >
                		<option value="0">请选择</option>
                		<c:forEach items="${menuIdList }" var="list">
                			<option value="${list.id }">${list.name }</option>
                		</c:forEach>
                	</select>
                </dd>
                </div>
                
                <dt>菜单类型：</dt>
                <dd>
                	<select name="type" id="menu_Type" onchange="urlAddShow(this.value);">
                		<option value="click">click</option>
                		<option value="view">view</option>
                		<option value="scancode_push">scancode_push</option>
                		<option value="scancode_waitmsg">scancode_waitmsg</option>
                		<option value="pic_sysphoto">pic_sysphoto</option>
                		<option value="pic_photo_or_album">pic_photo_or_album</option>
                		<option value="pic_weixin">pic_weixin</option>
                		<option value="location_select">location_select</option>
                	</select>
                </dd>
                
                <div id="urlAdd" style="display: none;">
                <dt>URL地址：</dt>
                <dd>
                	<input name="urlAddress" id="urlAddress" value=""  >
                </dd>
                </div>
                
                <div id="keyUpdate">
                <dt>KEY：</dt>
                <dd>
                	<input name="keyMenus" id="keyMenus" value=""  >
                </dd>
                </div>
               
			</dl>
		
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="updateDoFun()">保存</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#updateMenu').window('close');">取消</a>
		</div>
		</form>
	</div>

</body>
</html>