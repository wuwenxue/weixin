<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
    <meta name="keywords" content="jquery,ui,easy,easyui,web">  
    <meta name="description" content="easyui help you build your web page easily!">  
    <title>文章列表</title>  
    <link rel="stylesheet" type="text/css" href="themes/base.css" />
    <link rel="stylesheet" type="text/css" href="themes/bootstrap/easyui.css" />
    <link rel="stylesheet" type="text/css" href="themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="themes/style.css" />
    <script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>  
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/comboformat.js"></script>
	<script type="text/javascript" src="js/common-ningpai.js"></script>
    <script type="text/javascript">
	$(function(){
		
		$("#dg").datagrid({
			onDblClickRow:clickRow
			});
	 });
	function clickRow(rowIndex, rowData){
		
	}
	function formatcheckbox(val,row){
		return '<input type="checkbox" value="'+val+'" name="topicId"/>';
	}
    function doSearch(value,name){  
    	  var queryParams = $('#dg').datagrid('options').queryParams;  
        //更改queryParams对象中两属性。  
        queryParams.categoryId=$('#categoryId').combotree('getValue');
        
        queryParams.topicTitle = value;  
        $('#dg').datagrid('load');
      }  
</script>  
</head>  
<body>

	
	<div class="easyui-layout" data-options="fit : true,border : false">	
		
		<div data-options="region:'center',border:false">
			<table id="dg"  rownumbers="true" pagination="true" fitColumns="true"  singleSelect="true" toolbar="#toolbar" url="articlelistDo.htm"  data-options=" pageList:[100,50,30,15],remoteSort:true,sortName:'topicModifiedTime',
			sortOrder:'desc'">
	    	<thead>
	        	<tr>
	            	<th field="topicId" data-options="formatter:formatcheckbox" width="10" align="center">
	                  	  		  <a class="mr5" href="javascript:selectAll('topicId');">全选</a>
	                              <a class="ml5" href="javascript:unSelectAll('topicId');">反选</a>
	                </th>
	                <th field="title" width="35" sortable="true">文章标题</th>
					<th field="createTime" width="15" sortable="true">创建时间</th>
	            </tr>
	        </thead>
	    </table>
		</div>
	</div>
	
	
    <div id="toolbar" class="clearfix" style="padding:5px 10px;">  
         	<a class="easyui-linkbutton" data-options="plain:true" href="javascript:void(0)"  id="badd" onClick="toaddinformation();" iconcls="icon-add">添加</a>
		 	<a class="easyui-linkbutton" data-options="plain:true" href="javascript:void(0)"  id="bedit" onClick="toeditinformation();" iconcls="icon-edit">编辑</a> 
	 		<a class="easyui-linkbutton" data-options="plain:true" href="javascript:void(0)"  id="bdel" onClick="del();" iconcls="icon-remove">删除</a>
    </div>

<script type="text/javascript">

function toaddinformation(){
		window.location.href="toaddarticle.htm"; 
   	       	}

function toeditinformation(){
	 var row = $('#dg').datagrid('getSelected');  
     if (row){  
    	 window.location.href="toeditarticle.htm?id="+row.id; 
     } else{
     	$.messager.alert("提示","请选择一行编辑！");
     } 
     } 



function del(){  
 	var row = $('#dg').datagrid('getSelected');  
 		if(row){
				  $.messager.confirm('提示','你确定要删除此项吗?',function(r){  
			            if (r){  
				                $.post('delarticle.htm',{id:row.id},function(result){  
				                    if (result==1){  
				                        $('#dg').datagrid('reload');    // reload the user data 
				                        $.messager.show({   // show error message  
				                            title: '系统信息',  
				                            msg:'<span style="color:blue;">删除成功</span>'  
				                        });  
				                    } else {   
				                        $.messager.show({   // show error message  
				                            title: '系统信息',  
				                            msg:'<span style="color:red;">删除失败</span>'  
				                        });  
				                    }  
              						 },'json');  
          						}  
			        });  
		
	 	}else{
	     	$.messager.alert("提示","请选择一行删除！");
	    } 
    
	} 



//批量删除
function delalltopic(){
	var dlflag=false;
	var checkboxs = document.getElementsByName("topicId");
	for ( var i = 0; i < checkboxs.length; i++) {
		var e = checkboxs[i];
		if(e.checked==true){
			dlflag=true;
		}
	}
	
	if(dlflag){
	  $.messager.confirm('提示','你确定要删除所选数据?',function(r){  
		  if(r){
		    $('#delForm').form('submit',{  
		        url: 'delarticle.htm',  
		        onSubmit: function(){  
		            return $(this).form('validate');  
		        },  
		        success: function(result){  
		        	if (result==1){
                        $('#dg').datagrid('reload');    // reload the user data 
                        $.messager.show({   // show error message  
                            title: '系统信息',  
                            msg:'<span style="color:blue;">删除成功</span>'  
                        });  
                    } else { 
                        $.messager.show({   // show error message  
                            title: '系统信息',  
                            msg:'<span style="color:red;">删除失败</span>'  
                        });  
                    }  
		        }  
		    });  
		  }
	  });  	
	}else{
		  $.messager.alert( '提示','请点击多选,至少选择一行!');  
	}
}
</script>
</body>  
</html>