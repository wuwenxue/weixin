package co.dc.server.weixin.task;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import co.dc.server.weixin.service.AttentionService;

@Service("weixinTask")
public class WeixinTask {
	private static final Log log = LogFactory.getLog(WeixinTask.class);
	@Resource
	private AttentionService attentionService;
	
	public void task(){
		log.info("开始获取驾友网关注者列表");
		attentionService.saveJiayouAttention();
		log.info("开始获取盒子房关注者列表");
		attentionService.saveTikiboxAttention();
	}
	
}
