package co.dc.server.weixin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import co.dc.commons.dto.RetStatus;
import co.dc.commons.json.JSONObject;
import co.dc.commons.page.PageConstant;
import co.dc.commons.page.PageValItem;
import co.dc.commons.page.QueryResult;
import co.dc.commons.platform.PlatformConstant;
import co.dc.server.weixin.pojo.SenderToWeixinPojo;
import co.dc.server.weixin.pojo.WeixinToSenderPojo;
import co.dc.server.weixin.service.WeixinToSenderService;

@Controller
public class WeixinToSenderAction extends BaseAction {
	@Resource
	private WeixinToSenderService weixinToSenderService;
	
	
	@RequestMapping("/weixinRecord/weixinToSenderList")
	public ModelAndView list(String plat) {
		if(plat.equals("jiayou")){
			return new ModelAndView("weixinRecord/weixinToSenderList").addObject("plat", PlatformConstant.JIAYOU_WEB.getVal());
		}else if(plat.equals("tikibox")){
			return new ModelAndView("weixinRecord/weixinToSenderList").addObject("plat", PlatformConstant.TIKIBOX_WEB.getVal());
		}else{
			return new ModelAndView("weixinRecord/weixinToSenderList");
		}
	}
	
	@RequestMapping("/weixinRecord/weixinToSenderListDo")
	@ResponseBody
	public String listDo(HttpServletRequest request,int page, int rows,String plat) throws Exception {

		int pageindex = page * rows - rows;
		int maxsize = rows;
		List<PageValItem> whereList = new ArrayList<PageValItem>();
		if(StringUtils.isNotEmpty(plat)){
			PageValItem item = new PageValItem(PageConstant.EQ,"plat",plat);
			whereList.add(item);
		}
		QueryResult result = weixinToSenderService.pageList(whereList,pageindex, maxsize);
		Long total = result.getTotalrecord();
		List<SenderToWeixinPojo> list = result.getResultlist();
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		JSONObject json = new JSONObject(map);

		return json.toString();
	}
	
	
	@RequestMapping("/delWeixinToSenderDo")
	@ResponseBody
	public String delWeixinToSenderDo(long idsStr){
		
		RetStatus<WeixinToSenderPojo> retStatus = weixinToSenderService.delWeixinToSenderInfo(idsStr);
		
		return new JSONObject(retStatus).toString();
		
	}
}
