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
<title>上行列表</title>
  <%@include file="/WEB-INF/jsp/share/linkjs.jsp"%> 
    <script type="text/javascript">
	var plat = "";
    var dataGrid;
    $(function() {
        plat = '${plat}';
        dataGrid = $('#dataGrid').datagrid({
            url : 'weixinRecord/weixinToSenderListDo.html?plat='+plat,
            fit : true,
            idField : 'id',
            fitColumns : true,
            border : false,
            pagination : true,
            pageSize : 15,
            pageList : [ 15, 20, 30, 40, 50 ],
            selectOnCheck : true,
            singleSelect : true,
            nowrap : true,
            toolbar: '#toolbar',
            columns : [ [ {
                field : 'id',
                title : '编号',
                width : 2,
                checkbox : false
            },{
                field : 'fromUserName',
                title : '发送者',
                width :10,
            },{
                field : 'toUserName',
                title : '接收者',
                width :10,
            },{
                field : 'msgType',
                title : '消息类型',
                width :5,
            },{
                field : 'useNickname',
                title : '接收者昵称',
                width :5,
            },{
                field : 'useSex',
                title : '接收者性别',
                width :5,
                formatter:function(data,row,type){
                    if(row.useSex==1){
                        return "男";
                    }else if(row.useSex==2){
                        return "女";
                    }else if(row.useSex==0){
                        return "保密";
                    }else{
                        return "未知";
                    }
                }
            },{
                field : 'useAddress',
                title : '接收者地址',
                width :15,
            },{
                field : 'useHeadimgurl',
                title : '接收者头像',
                width :15,
            },{
                field : 'content',
                title : '消息内容',
                width :15,
            },{
                field : 'createDate',
                title : '发送时间',
                width :10,
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
                    $.post('delWeixinToSenderDo.html',{idsStr:idsStr}, function(data) {
                        var retobj = eval("("+data+")");
                        if(retobj.status==0){
                            $.messager.show({
                                title:'提示',
                                msg:'删除成功',
                                timeout:2000,
                                showType:'slide'
                            });
                        }else{
                            $.messager.show({
                                title:'提示',
                                msg:retobj.desc,
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
   
    </script>
    
</head>
<body>
    <div class="easyui-layout" data-options="fit : true,border : false">    
        
        <div data-options="region:'center',border:false">
            <table id="dataGrid"></table>
        </div>
    </div>
    
    <div id="toolbar" style="display: none;">
            <a onclick="delFun();" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
    </div>

</body>
</html>