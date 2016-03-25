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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@include file="/WEB-INF/jsp/share/linkjs.jsp"%> 
     
      
        <script type="text/javascript">

    	function addTab(title, url) {
    	    if ($("#tabs").tabs("exists", title)) {
    			$("#tabs").tabs("close",title);
    	    }
    		$("#tabs").tabs("add", {
    			title: title,
    			content: createFrame(url),
    			closable: true
    		});	
    		closeTab();
    		return false;
    	}

    	function closeTab() {
    		$(".tabs-inner").dblclick(function(){
    			var subtitle = $(this).children(".tabs-closable").text();
    			$('#tabs').tabs('close',subtitle);
    			});
    		$(".tabs-inner").click(function(e){
    			if(2 == e.which) {
    				var stitle = $(this).children(".tabs-closable").text();
    				$('#tabs').tabs('close',stitle);
    				};
    			});
    		};

    	function createFrame(url) {
    	    var s = '<iframe name="view_frame" scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
    	    return s;
    	}

        </script>
 
    </head>
    <body class="easyui-layout" >
        
        <div id="men" data-options="region:'west',split:true,title:'菜单'" style="width:180px;">
        	<div id="nav" class="easyui-accordion" data-options="border:false">
				<div title="盒子房微信管理">
					<ul>
						<li title="下行记录"><a class="b_blue" href="" onClick="return addTab('下行记录列表','weixinRecord/sendToWeixinList.html?plat=tikibox');">下行记录</a></li>
						<li title="上行记录"><a class="b_blue" href="" onClick="return addTab('上行记录列表','weixinRecord/weixinToSenderList.html?plat=tikibox');">上行记录</a></li>
						<li title="关注列表"><a class="b_blue" href="javascript:void(0)" onClick="return addTab('关注列表','attentionList.html?plat=tikibox');">关注列表</a></li>
						<li title="自定义菜单"><a class="b_blue" href="javascript:void(0)" onClick="return addTab('自定义菜单','menuList.html?plat=tikibox');">自定义菜单</a></li>
						<li title="图文管理"><a class="b_blue" href="javascript:void(0)" onClick="return addTab('图文管理','materialList.html?plat=tikibox');">图文管理</a></li>
						<li title="关键词回复"><a class="b_blue" href="javascript:void(0)" onClick="return addTab('关键词回复','replylist.html?plat=tikibox');">关键词回复</a></li>
						<li title="关注及卷首语设置"><a class="b_blue" href="javascript:void(0)" onClick="return addTab('关注及卷首语设置','indexReply.html?plat=tikibox');">关注及卷首语设置</a></li>
						<li title="微信官方平台"><a class="b_blue" href="javascript:void(0)" onClick="return addTab('微信官方平台','https://mp.weixin.qq.com');">微信官方平台</a></li>
					</ul>
				</div>	
				<div title="驾友网微信管理">
					<ul>
						<li title="下行记录"><a class="b_blue" href="" onClick="return addTab('下行记录列表','weixinRecord/sendToWeixinList.html?plat=jiayou');">下行记录</a></li>
						<li title="上行记录"><a class="b_blue" href="" onClick="return addTab('上行记录列表','weixinRecord/weixinToSenderList.html?plat=jiayou');">上行记录</a></li>
						<li title="关注列表"><a class="b_blue" href="javascript:void(0)" onClick="return addTab('关注列表','attentionList.html?plat=jiayou');">关注列表</a></li>
						<li title="自定义菜单"><a class="b_blue" href="javascript:void(0)" onClick="return addTab('自定义菜单','menuList.html?plat=jiayou');">自定义菜单</a></li>
						<li title="图文管理"><a class="b_blue" href="javascript:void(0)" onClick="return addTab('图文管理','materialList.html?plat=jiayou');">图文管理</a></li>
						<li title="关键词回复"><a class="b_blue" href="javascript:void(0)" onClick="return addTab('关键词回复','replylist.html?plat=jiayou');">关键词回复</a></li>
						<li title="关注及卷首语设置"><a class="b_blue" href="javascript:void(0)" onClick="return addTab('关注及卷首语设置','indexReply.html?plat=jiayou');">关注及卷首语设置</a></li>
						<li title="微信官方平台"><a class="b_blue" href="javascript:void(0)" onClick="return addTab('微信官方平台','https://mp.weixin.qq.com');">微信官方平台</a></li>
					</ul>
				</div>
			</div>
        </div>
        
        <div id="mainPanle" region="center" style="background:#eee; overflow-y:hidden">
            <div id="tabs" class="easyui-tabs" fit="true" border="false">
                <div title="欢迎使用" style="padding:20px;overflow:hidden;" >
                   <h3 class="f20 fb mt10" style="font-family:'微软雅黑';">欢迎使用驾友网后台管理系统</h3>
                   <div class="m_wp clearfix mt30">
                        <div class="easyui-panel" title="待处理事项" style="width:500px;">
                           <div class="pl_cont">
                               <h4 class="fb">订单：</h4>
                               <ul class="pl_list mt5 clearfix">
                                   <li class="have_new"><a href="javascript:;">未处理<em>(4)</em></a></li>
                                   <li><a href="javascript:;">待发货<em>(0)</em></a></li>
                                   <li><a href="javascript:;">新留言<em>(0)</em></a></li>
                               </ul>
                           </div>
                           <div class="pl_cont">
                               <h4 class="fb">会员：</h4>
                               <ul class="pl_list mt5 clearfix">
                                   <li><a href="javascript:;">新咨询<em>(0)</em></a></li>
                                   <li><a href="javascript:;">新留言<em>(0)</em></a></li>
                                   <li class="have_new"><a href="javascript:;">新评论<em>(6)</em></a></li>
                                   <li><a href="javascript:;">新消息<em>(0)</em></a></li>
                               </ul>
                           </div>
                     
                       </div>
                       
                       <div class="easyui-panel" title="业务概览" style="width:500px;"> 
                          <div class="pl_cont">
                               <h4 class="fb">订单：</h4>
                               <ul class="pl_list mt5 clearfix">
                                   <li><a href="javascript:;">今日订单<em>(0)</em></a></li>
                                   <li><a href="javascript:;">昨日订单<em>(0)</em></a></li>
                                   <li><a href="javascript:;">今日已付款<em>(0)</em></a></li>
                                   <li><a href="javascript:;">昨日已付款<em>(0)</em></a></li>
                               </ul>
                           </div>
                           <div class="pl_cont">
                               <h4 class="fb">会员：</h4>
                               <ul class="pl_list mt5 clearfix">
                                   <li><a href="javascript:;">今日新增<em id="todayCustomerCount">33</em></a></li>
                                   <li><a href="javascript:;">昨日新增<em id="yesterdayCustomerCount">33</em></a></li>
                                   <li class="have_new"><a href="javascript:;">会员总数<em id="customerCount">22</em></a></li>
                               </ul>
                           </div>
                         <div class="pl_cont">
                               <h4 class="fb">商品：</h4>
                               <ul class="pl_list mt5 clearfix">
                                   <li class="have_new"><a href="javascript:;">商品总数<em id="productCount">11</em></a></li>
                                   <li><a href="javascript:;">已下架商品<em>(0)</em></a></li>
                                   <li><a href="javascript:;">缺货商品<em>(0)</em></a></li>
                                   <li><a href="javascript:;">库存报警<em>(0)</em></a></li>
                               </ul>
                           </div>
                           <div class="pl_cont">
                               <h4 class="fb">促销：</h4>
                               <ul class="pl_list mt5 clearfix">
                                   <li class="have_new"><a href="javascript:;">商品促销<em>(1)</em></a></li>
                                   <li class="have_new"><a href="javascript:;">订单促销<em>(1)</em></a></li>
                                   <li class="have_new"><a href="javascript:;">优惠券<em>(1)</em></a></li>
                                   <li class="have_new"><a href="javascript:;">积分兑换赠品<em>(90)</em></a></li>
                               </ul>
                           </div> 
                    </div> 
                   </div>
                   <script>
                       $(function(){
                    	   $(".easyui-panel").each(function(){
                    		   $(this).find(".pl_cont:last").css('border-bottom','none');
                    	   });
                       });
                   </script>
                   
                </div>
            </div>
        </div>
    </body>
</html>
