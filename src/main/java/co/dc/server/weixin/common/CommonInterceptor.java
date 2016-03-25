package co.dc.server.weixin.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import co.dc.server.security.rmi.RmiSecurityService;
import co.dc.server.weixin.action.BaseAction;


public class CommonInterceptor implements HandlerInterceptor{
	private static final Log log = LogFactory.getLog(CommonInterceptor.class);
	@Resource
	private RmiSecurityService rmiSecurityService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		
		String ip = BaseAction.getRemoteAddrIp(request).trim();
		boolean flag = rmiSecurityService.isLimitIp(ip);
		if(flag){
			return true;
		}else{
			log.info("受限的ip["+ip+"]");
			return false;
		}		
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		
	}

	
}
