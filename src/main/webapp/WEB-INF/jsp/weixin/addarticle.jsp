<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加文章</title>
<link rel="stylesheet" type="text/css" href="themes/base.css" />
<link rel="stylesheet" type="text/css"
	href="themes/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="themes/icon.css" />
<link rel="stylesheet" type="text/css" href="themes/style.css" />
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/datagrid-detailview.js"></script>

<script type="text/javascript" src="locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/common-ningpai.js"></script>
<link rel="stylesheet"
	href="plugins/kindeditor/themes/default/default.css" />
<link rel="stylesheet"
	href="plugins/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="plugins/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="plugins/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8"
	src="plugins/kindeditor/plugins/code/prettify.js"></script>
<script type="text/javascript">
var editor1;
		$(function(){
			
				KindEditor.ready(function(K) {
					 editor1 = K.create('textarea[name="content"]', {
						cssPath : 'plugins/kindeditor/plugins/code/prettify.css',
						uploadJson : 'plugins/kindeditor/jsp/upload_json.jsp',
						allowFileManager : true,
						minHeight:300,
						minWidth:400,
						afterCreate : function() {
							var self = this;
							K.ctrl(document, 13, function() {
								self.sync();
								document.forms['example'].submit();
							});
							K.ctrl(self.edit.doc, 13, function() {
								self.sync();
								document.forms['example'].submit();
							});
						}
					});
					prettyPrint();
				});
		});
		
		

	function saveinfor(){ 
		
		editor1.sync();
		$('#inforForm').form('submit',{  
		    url: 'doaddarticle.htm',  
		    onSubmit: function(){  
		        return $(this).form('validate');  
		    },  
		    success: function(result){
		        if (result==1){  
		             $.messager.confirm('提示','<span style="color:blue;">添加成功！是否跳转到列表页面?</span>',function(r){ 
		            	 if(r){window.location.href='articlelist.htm'; }
		             });
		            
		        } else {  
		        	 $.messager.show({  
		                 title: '提示',  
		                 msg: '<span style="color:red;">操作失败</span>'
		             });  
		        }  
		    }  
		});  
		} 
		</script>
</head>
<body>
	<div class="wrapper">
		<div class="det_wp">
		<form method="post" id="inforForm">
		<!-- easyui-tabs -->
			<div class="easyui-tabs">
				<div title="文章详细">
					<dl class="form_dl  clearfix">
						<dt>资讯标题：</dt>
						<dd>
							<input class="text_300 easyui-validatebox" name="title" type="text"
								data-options="required:true"  />
						</dd>
						

						<dt>资讯内容：</dt>
						<dd>
							<textarea class="ff_textarea " name="content"></textarea>
						</dd>
					</dl>
				</div>
				
			</div>
			<!-- easyui-tabs -->
			</form>
		</div>
		<div class="mt10 tc">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
				href="javascript:void(0)" onClick="saveinfor();">保存</a>
		</div>
	</div>

</body>
</html>