package co.dc.server.weixin.action;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import co.dc.commons.dto.RetStatus;
import co.dc.commons.upyun.UpYun;
import co.dc.server.weixin.api.WeiXinManage;
import co.dc.server.weixin.rmi.RmiWeixinService;
import co.dc.server.weixin.rmi.TempQrDto;


@Controller
public class IndexAction extends BaseAction{

	@Resource
	private UpYun upyun;
	
	@Resource
	private RmiWeixinService rmiWeixinService;
	@RequestMapping("/index")
	public ModelAndView index(){
		return new ModelAndView("index");
	}
	
		
	
}
