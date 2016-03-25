package co.dc.server.weixin.action;

import co.dc.commons.json.JSONObject;
import co.dc.commons.page.QueryResult;
import co.dc.commons.platform.PlatformConstant;
import co.dc.server.weixin.service.AttentionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AttentionAction extends BaseAction {

	@Resource
	private AttentionService attentionService;

	@RequestMapping( "/attentionList" )
	public ModelAndView attentionList(String plat) {
		attentionService.saveJiayouAttention();
		if(plat.equals("jiayou")){
			return new ModelAndView("attention/attentionList").addObject("plat", PlatformConstant.JIAYOU_WEB.getVal());
		}else if(plat.equals("tikibox")){
			return new ModelAndView("attention/attentionList").addObject("plat", PlatformConstant.TIKIBOX_WEB.getVal());
		}else{
			return new ModelAndView("attention/attentionList");
		}
	}

	@RequestMapping("/attentionListDo")
	@ResponseBody
	public String attentionListDo(int page, int rows,String plat) {
		int pageindex = page * rows - rows;
		int maxsize = rows;
		QueryResult result = this.attentionService.listAttention(pageindex,maxsize,plat);
		Long total = Long.valueOf(result.getTotalrecord());
		List list = result.getResultlist();
		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);
		return new JSONObject(map).toString();
	}
}