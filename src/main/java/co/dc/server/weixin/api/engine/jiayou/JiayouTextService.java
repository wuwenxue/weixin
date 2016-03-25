package co.dc.server.weixin.api.engine.jiayou;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import co.dc.server.weixin.api.dto.BaseMsgDto;
import co.dc.server.weixin.api.dto.TextMsgDto;
import co.dc.server.weixin.dto.MaterialVo;
import co.dc.server.weixin.pojo.MaterialPojo;
import co.dc.server.weixin.pojo.ReplyKeyPojo;

@Service("jiayouTextService")
public class JiayouTextService {

	private static final String RESPONSE_TXT = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Content><![CDATA[%s]]></Content><FuncFlag>0</FuncFlag></xml>";
	
	@Resource
	private QueryRunner queryRunner;
	@Resource
	private JiayouContentService jiayouContentService;
	
	
	
	public String process(BaseMsgDto baseMsgDto) {
		
		TextMsgDto textMsgDto = (TextMsgDto) baseMsgDto;
		
		
		ReplyKeyPojo replyKey = jiayouContentService.reply(textMsgDto.getContent());
		if (replyKey != null) {
			if (replyKey.getReplyType() == 0) {
				if (StringUtils.isNotEmpty(replyKey.getComment())) {
					String message = replyKey.getComment();
					String s = String.format(RESPONSE_TXT, textMsgDto.getFromUserName(),
							textMsgDto.getToUserName(), System.currentTimeMillis(), "text",
							message);
					return s;
				}
			} else if (replyKey.getReplyType() == 1) {
				Long textImageId = replyKey.getMaterialId();
				MaterialPojo materialPojo = null;
				try {
					materialPojo = queryRunner.query(
							"select * from wx_material where id = ?",
							new BeanHandler<MaterialPojo>(MaterialPojo.class),
							textImageId);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				String textImageXml = materialPojo.getXml();
				textImageXml = MaterialVo.convertXml(textImageXml,materialPojo.getId());


			
				String s = String.format(textImageXml, textMsgDto.getFromUserName(),
						textMsgDto.getToUserName(), System.currentTimeMillis());
				return s;
			} else if (replyKey.getReplyType() == 2) {

			} else if(replyKey.getReplyType()==3){
				String textImageXml = MaterialVo.imgXml(replyKey.getMediaId());
			
				String s = String.format(textImageXml, textMsgDto.getFromUserName(),
						textMsgDto.getToUserName(), System.currentTimeMillis());
				return s;
			}

		}else {
				String s = String
						.format(RESPONSE_TXT, textMsgDto.getFromUserName(), textMsgDto.getToUserName(),
								System.currentTimeMillis(), "text", jiayouContentService.preface());
				return s;
		}
		
		
		return "";
	}
}
