package co.dc.server.weixin.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import co.dc.commons.json.JSONArray;
import co.dc.commons.json.JSONObject;
import co.dc.commons.platform.PlatformConstant;
import co.dc.commons.upyun.UpYun;
import co.dc.commons.utils.DateTimeUtil;
import co.dc.server.weixin.dto.MaterialVo;
import co.dc.server.weixin.pojo.ArticlePojo;
import co.dc.server.weixin.pojo.MaterialPojo;


@Controller
public class MaterialAction {

	@Resource
	private QueryRunner queryRunner;
	
	@RequestMapping(value="/materialList")
	public ModelAndView list(String plat){
		if(plat.equals("jiayou")){
			return new ModelAndView("weixin/materiallist").addObject("plat", PlatformConstant.JIAYOU_WEB.getVal());
		}else if(plat.equals("tikibox")){
			return new ModelAndView("weixin/materiallist").addObject("plat", PlatformConstant.TIKIBOX_WEB.getVal());
		}else{
			return new ModelAndView("weixin/materiallist");
		}
	}
	
	@Resource
	private UpYun upyun;
	
	@RequestMapping(value="/materialListDo")
	@ResponseBody
	public String listDo(HttpServletResponse response,int page,int rows,String plat){
		try{
			int beg = page * rows-rows;
			int end = rows;
			Long total = queryRunner.query("select count(*) from wx_material where type='tuwen'", new ScalarHandler<Long>());
			List<MaterialPojo> list = queryRunner.query("select * from wx_material where type='tuwen' and plat=? limit ?,?", new BeanListHandler<MaterialPojo>(MaterialPojo.class),plat ,beg,end);
			Map pageMap = new HashMap();
			pageMap.put("total", total);
			pageMap.put("rows", list);
			JSONObject jsonObject = new JSONObject(pageMap);
			return jsonObject.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/materialAdd")
	public ModelAndView tadd(int num,String plat){
		ModelAndView andView = new ModelAndView("weixin/materialadd");
		andView.addObject("num", num);
		andView.addObject("plat", plat);
		try {
			List<ArticlePojo> articleList = queryRunner.query("select * from wx_article ", new BeanListHandler<ArticlePojo>(ArticlePojo.class));
			andView.addObject("articleList", articleList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return andView;
	}
	
	@RequestMapping(value="/materialAddDo")
	@ResponseBody
	public String taddDo(HttpServletResponse response,String title,String xml){
		try {
			int ret = queryRunner.update("insert into wx_material (name, type,xml,createtime) values (?,?,?,?)", title,"tuwen",xml,DateTimeUtil.getDateTimeString());
			if(ret>0){
				return "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return null;
	}
	
	@RequestMapping(value="/materialupdate")
	public ModelAndView materialupdate(long id,String plat){
		ModelAndView andView = new ModelAndView("weixin/materialupdate");
		andView.addObject("plat", plat);
		try {
			
			MaterialPojo material = queryRunner.query("select * from wx_material where id = ?", new BeanHandler<MaterialPojo>(MaterialPojo.class),id);
			List<MaterialVo> maList = MaterialVo.convertList(material.getXml());
			andView.addObject("num", maList.size());
			andView.addObject("material",material);
			andView.addObject("maList",maList);
			List<ArticlePojo> articleList = queryRunner.query("select * from wx_article ", new BeanListHandler<ArticlePojo>(ArticlePojo.class));
			andView.addObject("articleList", articleList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return andView;
	}
	
	@RequestMapping(value="/materialUpdateDo")
	@ResponseBody
	public String materialUpdateDo(HttpServletResponse response,String title,String xml){
		try {
			int ret = queryRunner.update("insert into wx_material (name, type,xml,createtime) values (?,?,?,?)", title,"tuwen",xml,DateTimeUtil.getDateTimeString());
			if(ret>0){
				return "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return null;
	}
	
	@RequestMapping(value="/ajaxMaterialAddDo")
	@ResponseBody
	public String ajaxMaterialAddDo(HttpServletRequest httpRequest,HttpServletResponse response,int num,String plat){
		List<MaterialVo> _list = new ArrayList<MaterialVo>();
		String _title = "";
		for(int i=1;i<=num;i++){
			String title = (String)httpRequest.getParameter("title"+i);
			if(i==1){
				_title = title;
			}
			String materialPath = (String)httpRequest.getParameter("materialPic"+i);
			String note = (String)httpRequest.getParameter("note"+i);
			String source = (String)httpRequest.getParameter("source"+i);
			String contentDesc = (String)httpRequest.getParameter("contentDesc"+i);
			MaterialVo materialVo = new MaterialVo();
			materialVo.setNum(i);
			materialVo.setTitle(title);
			materialVo.setNote(note);
			materialVo.setMaterialPath(materialPath);
			materialVo.setSource(source);
			materialVo.setContentDesc(contentDesc);
			_list.add(materialVo);
		}

		JSONArray jsonArray = new JSONArray(_list);
		
		try {
			int ret = queryRunner.update("insert into wx_material (name, type,xml,createtime,plat) values (?,?,?,?,?)", _title,"tuwen",jsonArray.toString(),DateTimeUtil.getDateTimeString(),plat);
			if(ret>0){
				return "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return null;
	}
	
	
	@RequestMapping(value="/ajaxMaterialUpdateDo")
	@ResponseBody
	public String ajaxMaterialUpdateDo(HttpServletRequest httpRequest,HttpServletResponse response,int num,long id){
		List<MaterialVo> _list = new ArrayList<MaterialVo>();
		String _title = "";
		for(int i=1;i<=num;i++){
			String title = (String)httpRequest.getParameter("title"+i);
			if(i==1){
				_title = title;
			}
			String materialPath = (String)httpRequest.getParameter("materialPic"+i);
			String note = (String)httpRequest.getParameter("note"+i);
			String source = (String)httpRequest.getParameter("source"+i);
			String contentDesc = (String)httpRequest.getParameter("contentDesc"+i);
			MaterialVo materialVo = new MaterialVo();
			materialVo.setNum(i);
			materialVo.setTitle(title);
			materialVo.setNote(note);
			materialVo.setMaterialPath(materialPath);
			materialVo.setSource(source);
			materialVo.setContentDesc(contentDesc);
			_list.add(materialVo);
		}

		JSONArray jsonArray = new JSONArray(_list);
		
		try {
			int ret = queryRunner.update("update wx_material set name=?,xml=?  where id = ?", _title,jsonArray.toString(),id);
			if(ret>0){
				return "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return null;
	}
	
	
	/**
	 * 单个删除资讯
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping("/materialdeldo")
	@ResponseBody
	public String materialdeldo(HttpServletResponse response,HttpServletRequest request,Long id ){
		int flag=0;
		try {
		
			int ret = queryRunner.update("delete from wx_material  where id = ?",id);
			if(ret>0){
				return "success";
			}else{
				return "0";
			}
		} catch (Exception e) {
		}
		return null;
	}
	@RequestMapping(value="/materialAjaxImage")
	@ResponseBody
	public String ajaxImage(HttpServletResponse response,MultipartHttpServletRequest request,String flag){
		
		MultipartFile materialFile = request.getFile(flag);
		
		String fileContentType = materialFile.getContentType();
		
		try {
			if(!fileContentType.equals("image/bmp")&&!fileContentType.equals("image/gif")
					&&!fileContentType.equals("image/jpeg")&&!fileContentType.equals("image/png")){
				
				return "{\"status\":\"exterror\",\"message\":\"error\"}";
				
			}

			
			boolean uploadflag = upyun.uploadFile(materialFile.getOriginalFilename(),materialFile.getBytes());
			
			if(uploadflag){
				String path = upyun.getPath();
				
				return "{\"status\":\"success\",\"message\":\""+path+"\"}";
			}
			return "{\"status\":\"error\",\"message\":\"error\"}";
		}catch (IOException e) {
			return "{\"status\":\"error\",\"message\":\"error\"}";
			
        } 
	}
	
}
