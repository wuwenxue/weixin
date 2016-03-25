package co.dc.server.weixin.action;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import co.dc.commons.json.JSONObject;
import co.dc.commons.platform.PlatformConstant;
import co.dc.server.weixin.api.JiayouWeiXinManage;
import co.dc.server.weixin.api.TikiboxWeiXinManage;
import co.dc.server.weixin.common.util.Upload;
import co.dc.server.weixin.pojo.MaterialPojo;
import co.dc.server.weixin.pojo.ReplyKeyPojo;


@Controller
public class ReplyAction {

	@Resource
	private QueryRunner queryRunner;
	@Resource
	private JiayouWeiXinManage jiayouWeiXinManage;
	@Resource
	private TikiboxWeiXinManage tikiboxWeiXinManage;
	
	@RequestMapping(value="/replylist")
	public ModelAndView list(String plat){
		if(plat.equals("jiayou")){
			return new ModelAndView("weixin/replylist").addObject("plat", PlatformConstant.JIAYOU_WEB.getVal());
		}else if(plat.equals("tikibox")){
			return new ModelAndView("weixin/replylist").addObject("plat", PlatformConstant.TIKIBOX_WEB.getVal());
		}else{
			return new ModelAndView("weixin/replylist");
		}
	}
	
	@RequestMapping(value="/replylistDo")
	@ResponseBody
	public String listDo(HttpServletResponse response,int page,int rows,String plat){
		try{
			int beg = page * rows-rows;
			int end = rows;
			Long total = queryRunner.query("select count(*) from wx_replykey where plat=? order by washis,command", new ScalarHandler<Long>(),plat);
			List<ReplyKeyPojo> list = queryRunner.query("select * from wx_replykey where plat=? order by washis,command*1 limit ?,?", new BeanListHandler<ReplyKeyPojo>(ReplyKeyPojo.class),plat, beg,end);
			
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
	
	@RequestMapping(value="/replyAdd")
	public ModelAndView replyAdd(String plat){
		ModelAndView modelAndView = new ModelAndView("weixin/replyadd").addObject("plat", plat);
		try {
			
			List<MaterialPojo> list = queryRunner.query("select * from wx_material where plat=? and type='tuwen'", new BeanListHandler<MaterialPojo>(MaterialPojo.class),plat);
			modelAndView.addObject("materiallist", list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value="/replyAddDo")
	@ResponseBody
	public String taddDo(HttpServletRequest request,HttpServletResponse response,ReplyKeyPojo replyKey,String plat,@RequestParam MultipartFile picUrl){
		try {
			if(replyKey.getReplyType()==3){
				String msg = getMedia(picUrl,plat);
				if(!msg.equals("1")){
					replyKey.setComment(msg);
				}else{
					return "error";
				}
			}
			
			int ret = queryRunner.update("insert into wx_replykey "
					+ "(name,command,keyworld,comment,replytype,materialid,washis,inlayid,plat) values (?,?,?,?,?,?,?,?,?)",
					replyKey.getName(),replyKey.getCommand(),replyKey.getKeyworld(),replyKey.getComment(),replyKey.getReplyType(),replyKey.getMaterialId(),replyKey.getWashis(),replyKey.getInlayId(),plat);
			if(ret>0){
				return "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  "error";
		}
		return null;
	}
	@RequestMapping(value="/indexReply")
	public ModelAndView indexReply(HttpServletResponse response,String plat){
		ModelAndView modelAndView = new ModelAndView("weixin/indexReply");
		try {
			if(plat.equals("jiayou")){
				plat = PlatformConstant.JIAYOU_WEB.getVal();
			}else if(plat.equals("tikibox")){
				plat = PlatformConstant.TIKIBOX_WEB.getVal();
			}else{
				plat = "";
			}
			Map<String, Object> map = queryRunner.query("select * from wx_index_reply where plat=?", new MapHandler(),plat);
			if(map!=null){
				modelAndView.addObject("preface", map.get("preface"));
				modelAndView.addObject("busperface", map.get("busperface"));
				modelAndView.addObject("keep", map.get("keep"));
				modelAndView.addObject("loginpreface", map.get("loginpreface"));
			}
			modelAndView.addObject("plat", plat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/indexReplyDo")
	@ResponseBody
	public String indexReplyDo(HttpServletResponse response,String preface,String keep,String busperface,String loginpreface,String plat){
		try {
			if(StringUtils.isEmpty(plat)){
				return "errorPlat";
			}
			Long count = queryRunner.query("select count(*) from wx_index_reply where plat=?",new ScalarHandler<Long>(), plat);
			if(count>0){
				int ret = queryRunner.update("update wx_index_reply set preface = ? , keep = ?,busperface=?,loginpreface=? where plat=?", preface,keep,busperface,loginpreface,plat);
				if(ret>0){
					return "success";
				}
			}else{
				int ret = queryRunner.update("insert into wx_index_reply(busperface,preface,keep,loginpreface,plat) values(?,?,?,?,?)", busperface,preface,keep,loginpreface,plat);
				if(ret>0){
					return "success";
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		} 
		return null;
	
	}
	
	
	
	@RequestMapping(value="/replyUpdate")
	public ModelAndView replyUpdate(HttpServletResponse response,long id,String plat){
		ModelAndView modelAndView = new ModelAndView("weixin/replyupdate");
		try {
			ReplyKeyPojo replyKey = queryRunner.query("select * from wx_replykey where id=?", new BeanHandler<ReplyKeyPojo>(ReplyKeyPojo.class),id);
			if(replyKey.getReplyType()==3){
				if(plat.equals(PlatformConstant.JIAYOU_WEB.getVal())){
					modelAndView.addObject("accessToken", jiayouWeiXinManage.getTempAccessToken());
				}else if(plat.equals(PlatformConstant.TIKIBOX_WEB.getVal())){
					modelAndView.addObject("accessToken", tikiboxWeiXinManage.getTempAccessToken());
				}
			}
			modelAndView.addObject("obj", replyKey);
			List<MaterialPojo> list = queryRunner.query("select * from wx_material where plat=? and type='tuwen'", new BeanListHandler<MaterialPojo>(MaterialPojo.class),plat);
			modelAndView.addObject("materiallist", list);
			modelAndView.addObject("plat", plat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value="/replyUpdateDo")
	@ResponseBody
	public String replyUpdateDo(HttpServletResponse response,ReplyKeyPojo replyKey,@RequestParam MultipartFile picUrl,String plat){
		try {
			if(replyKey.getReplyType()==3){
				String msg = getMedia(picUrl,plat);
				if(!msg.equals("1")){
					replyKey.setComment(msg);
				}else{
					return "error";
				}
			}
			int ret = queryRunner.update("update wx_replykey set "
					+ "name = ?, command = ?,keyworld=?,comment=?,replytype=?,materialid=?,washis=? where id = ?",
					replyKey.getName(),replyKey.getCommand(),replyKey.getKeyworld(),replyKey.getComment(),replyKey.getReplyType(),replyKey.getMaterialId(),replyKey.getWashis(),replyKey.getId());
			if(ret>0){
				return "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/replyDelDo")
	@ResponseBody
	public String replyDelDo(HttpServletResponse response,long id){
		try {
			int ret = queryRunner.update("delete from wx_replykey where id=? ",id);
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
	
	public String getMedia(@RequestParam MultipartFile picUrl,String plat){
        Upload upload = new Upload();
        String msg = "";
        try {
        	if(plat.equals(PlatformConstant.JIAYOU_WEB.getVal())){
        		msg = jiayouWeiXinManage.uploadMedia(upload.uploadImage(picUrl), "image");
        	}else if(plat.equals(PlatformConstant.TIKIBOX_WEB.getVal())){
        		msg = tikiboxWeiXinManage.uploadMedia(upload.uploadImage(picUrl), "image");
        	}
        	
        }catch(Exception e){
        	e.printStackTrace();
        }
		return msg;
	}
}
