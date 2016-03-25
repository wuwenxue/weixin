/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.dc.server.weixin.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;






import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import co.dc.commons.json.JSONObject;
import co.dc.commons.utils.DateTimeUtil;
import co.dc.server.weixin.pojo.ArticlePojo;

/**
 * 咨询Controller
 * @author guoguangnan
 *
 */
@Controller
public class ArticleAction {
	@Resource
	private QueryRunner queryRunner;
	/**
	 * 跳转资讯列表
	 * @param request
	 * @param groupId
	 * @return ModelAndView
	 */
	@RequestMapping(value ="/articlelist", method = RequestMethod.GET)
	public ModelAndView information(HttpServletRequest request,String groupId){
		return new ModelAndView("weixin/articlelist");
	}
	/**
	 * 查询资讯信息列表
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping("/articlelistDo")
	@ResponseBody
	public String informationList(HttpServletResponse response,int page,int rows){
		try {
			int beg = page * rows-rows;
			int end = rows;
			Long total = queryRunner.query("select count(*) from wx_article ", new ScalarHandler<Long>());
			List<ArticlePojo> list = queryRunner.query("select * from wx_article limit ?,?", new BeanListHandler<ArticlePojo>(ArticlePojo.class), beg,end);
			Map pageMap = new HashMap();
			pageMap.put("total", total);
			pageMap.put("rows", list);
			JSONObject jsonObject = new JSONObject(pageMap);
			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return null;
	}
	
	/**
	 * 去添加资讯
	 * @return  ModelAndView
	 */
	@RequestMapping("/toaddarticle")
	public ModelAndView toAddInformation(){
		return new ModelAndView("weixin/addarticle"); 
	}
	
	/**
	 * 确定添加资讯
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping("/doaddarticle")
	@ResponseBody
	public String doAddInformation(HttpServletResponse response,HttpServletRequest request,ArticlePojo article ){
		int flag=0;
		try {
			article.setCreateTime(DateTimeUtil.getDateTimeString());
			int ret = queryRunner.update("insert into wx_article "
					+ "(title,createtime,content) values (?,?,?)",
					article.getTitle(),article.getCreateTime(),article.getContent());
			if(ret>0){
				return "1";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
		return null;
	}
	
	/**
	 * 去编辑资讯
	 * @return  ModelAndView
	 */
	@RequestMapping("/toeditarticle")
	public ModelAndView toEditInformation(Long id){
		ModelAndView mav =new ModelAndView("weixin/editarticle");
		ArticlePojo article = null;
		try {
			article = queryRunner.query("select * from wx_article where id=?", new BeanHandler<ArticlePojo>(ArticlePojo.class),id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mav.addObject("obj", article);
		return  mav;
	}

	/**
	 * 确定编辑资讯
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping("/doeditarticle")
	@ResponseBody
	public String doEditInformation(HttpServletResponse response,HttpServletRequest request,ArticlePojo article){
		int flag=0;
		try {
			int ret = queryRunner.update("update wx_article set title = ?, content = ? where id = ?",
					article.getTitle(),article.getContent(),article.getId());
			if(ret>0){
				return "1";
			}else{
				return "0";
			}
		} catch (Exception e) {
		} 
		return null;
	}
	
	
	/**
	 * 单个删除资讯
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping("/delarticle")
	@ResponseBody
	public String delInformation(HttpServletResponse response,HttpServletRequest request,Long id ){
		int flag=0;
		try {
		
			int ret = queryRunner.update("delete from wx_article  where id = ?",id);
			if(ret>0){
				return "1";
			}else{
				return "0";
			}
		} catch (Exception e) {
		} 
		return null;
	}
	
	
	/**
	 * 批量删除资讯
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping("/delallarticle")
	@ResponseBody
	public String delAllInformation(HttpServletResponse response,HttpServletRequest request,Long[] topicId ){
		
		return null;
	}
	
	
}
