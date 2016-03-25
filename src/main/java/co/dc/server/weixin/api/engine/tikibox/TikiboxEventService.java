package co.dc.server.weixin.api.engine.tikibox;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import co.dc.server.weixin.api.dto.BaseMsgDto;
import co.dc.server.weixin.api.dto.EventMsgTypeDto;
import co.dc.server.weixin.rmi.TempQrDto;

@Service("tikiboxEventService")
public class TikiboxEventService {

	private static final String RESPONSE_TXT = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Content><![CDATA[%s]]></Content><FuncFlag>0</FuncFlag></xml>";

	@Resource(name = "tempQrCache")
	private Cache tempQrCache;
	@Resource
	private TikiboxContentService tikiBoxContentService;

	public String process(BaseMsgDto baseMsgDto) {
		EventMsgTypeDto eventMsgTypeDto = (EventMsgTypeDto) baseMsgDto;
		String event = eventMsgTypeDto.getEvent();
		String eventKey = eventMsgTypeDto.getEventKey();
		if (event.equals("SCAN")) {// 扫码事件
			String ticket = eventMsgTypeDto.getTicket();
			net.sf.ehcache.Element cacheElement = tempQrCache.get(ticket);
			if (cacheElement != null) {// 扫码登录
				TempQrDto tempQrDto = (TempQrDto) cacheElement.getObjectValue();
				if (tempQrDto.getAction().equals("login")) {
					tempQrDto.setStatus("ok");
					tempQrDto.setWid(baseMsgDto.getFromUserName());
					String s = String.format("欢迎回来，即将为您登录盒子房网站!!!",
							baseMsgDto.getFromUserName(),
							baseMsgDto.getToUserName(),
							System.currentTimeMillis());
					return s;

				}
			}
		} else if (event.equals("scancode_waitmsg")) {// 菜单扫描
			if (eventKey.equals("login")) {// 设定的登录
				String ticket = eventMsgTypeDto.getScanResult();
				net.sf.ehcache.Element cacheElement = tempQrCache.get(ticket);
				if (cacheElement != null) {
					TempQrDto tempQrDto = (TempQrDto) cacheElement
							.getObjectValue();
					if (tempQrDto.getAction().equals("login")) {
						tempQrDto.setStatus("ok");
						tempQrDto.setWid(eventMsgTypeDto.getFromUserName());
						String s = String.format("欢迎回来，即将为您登录盒子房网站!!!",
								eventMsgTypeDto.getFromUserName(),
								eventMsgTypeDto.getToUserName(),
								System.currentTimeMillis());
						return s;
					}
				}
			}

		} else if (event.equals("subscribe")) {// 关注
			if (StringUtils.isNotEmpty(eventKey)
					&& StringUtils.isNotEmpty(eventMsgTypeDto.getTicket())) {
				net.sf.ehcache.Element cacheElement = tempQrCache
						.get(eventMsgTypeDto.getTicket());
				if (cacheElement != null) {// 扫码登陆的
					TempQrDto tempQrDto = (TempQrDto) cacheElement
							.getObjectValue();
					if (tempQrDto.getAction().equals("login")) {
						tempQrDto.setStatus("ok");
						tempQrDto.setWid(eventMsgTypeDto.getFromUserName());
						String s = String.format("欢迎关注盒子房，即将为您登录盒子房网站!!!",
								eventMsgTypeDto.getFromUserName(),
								eventMsgTypeDto.getToUserName(),
								System.currentTimeMillis());
						return s;

					}
				} else {// 扫码关注的
					return String.format(RESPONSE_TXT,
							eventMsgTypeDto.getFromUserName(),
							eventMsgTypeDto.getToUserName(),
							System.currentTimeMillis(), "text",
							tikiBoxContentService.keep());
				}

			} else {

			}
		}

		return "";
	}

}
