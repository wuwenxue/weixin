package co.dc.server.weixin.api.engine.jiayou;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import co.dc.server.weixin.api.dto.BaseMsgDto;
import co.dc.server.weixin.api.dto.EventMsgTypeDto;
import co.dc.server.weixin.rmi.TempQrDto;

@Service("jiayouEventService")
public class JiayouEventService {

	private static final String RESPONSE_TXT = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Content><![CDATA[%s]]></Content><FuncFlag>0</FuncFlag></xml>";

	@Resource(name = "couponTemQrCache")
	private Cache couponTemQrCache;
	@Resource
	private JiayouContentService jiayouContentService;
	@Resource
	private JiayouTextService jiayouTextService;

	public String process(BaseMsgDto baseMsgDto) {
		EventMsgTypeDto eventMsgTypeDto = (EventMsgTypeDto) baseMsgDto;
		String event = eventMsgTypeDto.getEvent();
		String eventKey = eventMsgTypeDto.getEventKey();
		if (event.equals("SCAN")) {// 扫码事件
			String ticket = eventMsgTypeDto.getTicket();
			net.sf.ehcache.Element cacheElement = couponTemQrCache.get(ticket);
			if (cacheElement != null) {// 扫码使用优惠券
				TempQrDto tempQrDto = (TempQrDto) cacheElement.getObjectValue();
				if (tempQrDto.getAction().equals("coupon")) {
					tempQrDto.setStatus("ok");
					tempQrDto.setWid(baseMsgDto.getFromUserName());
					String s = String.format("您好,洗车优惠券已使用成功！",
							baseMsgDto.getFromUserName(),
							baseMsgDto.getToUserName(),
							System.currentTimeMillis());
					return s;

				}
			}
		} else if (event.equals("scancode_waitmsg")) {// 菜单扫描
			if (eventKey.equals("login")) {// 设定的登录
				String ticket = eventMsgTypeDto.getScanResult();
				net.sf.ehcache.Element cacheElement = couponTemQrCache.get(ticket);
				if (cacheElement != null) {
					TempQrDto tempQrDto = (TempQrDto) cacheElement
							.getObjectValue();
					if (tempQrDto.getAction().equals("coupon")) {
						tempQrDto.setStatus("ok");
						tempQrDto.setWid(eventMsgTypeDto.getFromUserName());
						String s = String.format("您好,洗车优惠券已使用成功！",
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
				net.sf.ehcache.Element cacheElement = couponTemQrCache
						.get(eventMsgTypeDto.getTicket());
				if (cacheElement != null) {// 扫码使用优惠券
					TempQrDto tempQrDto = (TempQrDto) cacheElement
							.getObjectValue();
					if (tempQrDto.getAction().equals("coupon")) {
						tempQrDto.setStatus("ok");
						tempQrDto.setWid(eventMsgTypeDto.getFromUserName());
						String s = String.format("欢迎加入驾友网",
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
							jiayouContentService.keep());
				}

			} else {

			}
		}else if (event.equals("CLICK")){
			return jiayouTextService.process(baseMsgDto);
		}

		return "";
	}

}
