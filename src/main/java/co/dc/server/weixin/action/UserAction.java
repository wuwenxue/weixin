package co.dc.server.weixin.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction{
	
	@RequestMapping("/list")
	public ModelAndView list(){
		return new ModelAndView("user/list");
	}
	
	
}
