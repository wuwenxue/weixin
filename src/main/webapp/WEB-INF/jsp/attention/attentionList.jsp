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
    		url : 'attentionListDo.html?plat=${plat}',
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
    		columns : [ [ {
    			field : 'id',
    			title : '编号',
    			width : 10,
    			checkbox : false
    		},{
    			field : 'wid',
    			title : '关注者微信Id',
    			width :10
    		},{
    			field : 'nickname',
    			title : '昵称',
    			width :10
    		},{
    			field : 'sex',
    			title : '性别',
    			width :10,
    			formatter:function(data,row,type){
    				if(row.sex==1){
    					return "男";
    				}else if(row.sex==2){
    					return "女";
    				}else if(row.sex==0){
    					return "保密";
    				}else{
    					return "未知";
    				}
    			}
    		},{
    			field : 'address',
    			title : '地址',
    			width :10
    		},{
    			field : 'headimgurl',
    			title : '头像',
    			width :10
    		},{
    			field : 'subscribeTime',
    			title : '关注时间',
    			width :10
    		}
    		
    		] ]
    		
    	});
    	
    });

    </script>
</head>
<body >
	<div class="easyui-layout" data-options="fit : true,border : false">

		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>


</body>
</html>