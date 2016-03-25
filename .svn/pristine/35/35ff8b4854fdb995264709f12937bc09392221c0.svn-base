package co.dc.server.weixin.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class BaseAction {
	
	
	public ModelAndView alert(String message,String url){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("share/alert");
		modelAndView.addObject("message", message);
		return modelAndView;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	
	
	public static String getRemoteAddrIp(HttpServletRequest request) {  
	    String ipFromNginx = getHeader(request, "X-Real-IP");  
	    return StringUtils.isEmpty(ipFromNginx) ? getIpAddr(request) : ipFromNginx;  
	}  
	  
	  
	private static String getHeader(HttpServletRequest request, String headName) {  
	    String value = request.getHeader(headName);  
	    return !StringUtils.isBlank(value) && !"unknown".equalsIgnoreCase(value) ? value : "";  
	}
}
