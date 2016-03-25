package co.dc.server.weixin.api.engine.tikibox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import co.dc.commons.json.JSONArray;
import co.dc.commons.platform.PlatformConstant;
import co.dc.commons.utils.DateTimeUtil;
import co.dc.server.member.dto.MemberDto;
import co.dc.server.member.rmi.RmiMemberServer;
import co.dc.server.weixin.api.TikiboxWeiXinManage;
import co.dc.server.weixin.api.dto.BaseMsgDto;
import co.dc.server.weixin.api.dto.EventMsgTypeDto;
import co.dc.server.weixin.api.dto.TextMsgDto;
import co.dc.server.weixin.dto.MaterialVo;
import co.dc.server.weixin.pojo.AttentionPojo;
import co.dc.server.weixin.pojo.MaterialPojo;
import co.dc.server.weixin.pojo.ReplyKeyPojo;
import co.dc.server.weixin.rmi.TempQrDto;

@Service("tikiboxEngineService")
public class TikiboxEngineService {

	private static Log log = LogFactory.getLog(TikiboxEngineService.class);
	
	@Resource
	private RmiMemberServer rmiMemberServer;
	@Resource
	private TikiboxWeiXinManage tikiboxWeiXinManage;
	@Resource
	private TikiboxEventService tikiboxEventService;
	@Resource
	private TikiboxTextService tikiboxTextService;

	private String plat = PlatformConstant.TIKIBOX_WEB.getVal();
	

	

	class MapKeyComparator implements Comparator<Double> {
		public int compare(Double str1, Double str2) {
			return str1.compareTo(str2);
		}
	}

	

	public String process(BaseMsgDto baseMsgDto) {

		// 关注语和卷首语
		try {
			MemberDto memberDto = rmiMemberServer.loginByWid(baseMsgDto
					.getFromUserName(),PlatformConstant.TIKIBOX_WEB.getVal());

			try {
				if (memberDto != null) {
					if (StringUtils.isEmpty(memberDto.getName())) {
						AttentionPojo attentionPojo = tikiboxWeiXinManage
								.attentionInfo(memberDto.getWid());
						memberDto.setName(attentionPojo.getNickname());
						rmiMemberServer.updateUserMessage(memberDto);
					}
				}
			} catch (Exception e) {
				log.error("update member info error", e);
			}

			// 消息类型
			String msgType = baseMsgDto.getMsgType();
			if (msgType.equals("event")) {
				return tikiboxEventService.process(baseMsgDto);
			} else if (msgType.equals("text")) {
				return tikiboxTextService.process(baseMsgDto);
			}
		} catch (Exception e1) {
			log.error("解析异常报文异常", e1);
		}

		
		return null;
	}



}
