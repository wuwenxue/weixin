package co.dc.server.weixin.api.engine.tikibox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import co.dc.commons.platform.PlatformConstant;
import co.dc.server.weixin.pojo.ReplyKeyPojo;

@Service("tikiboxContentService")
public class TikiboxContentService {
	private static Log log = LogFactory.getLog(TikiboxContentService.class);
	private String plat = PlatformConstant.TIKIBOX_WEB.getVal();
	@Resource
	private QueryRunner queryRunner;
	private Map<String, ReplyKeyPojo> replyMap = new HashMap<String, ReplyKeyPojo>();
	
	
	// 关注语
	public String keep() {
		try {
			return queryRunner.query(
					"select keep from wx_index_reply where plat = ?",
					new ScalarHandler<String>(), plat);
		} catch (SQLException e) {
			log.error("keep error", e);
			return "";
		}
	}

	// 卷首语
	public String preface() {
		try {
			return queryRunner.query(
					"select preface from wx_index_reply where plat = ?",
					new ScalarHandler<String>(), plat);
		} catch (SQLException e) {
			log.error("keep error", e);
			return "";
		}
	}
	
	
	public ReplyKeyPojo reply(String content){
		
		List<ReplyKeyPojo> list = new ArrayList<ReplyKeyPojo>();
		try {
			list = queryRunner.query(
					"select * from wx_replykey where plat = ? ",
					new BeanListHandler<ReplyKeyPojo>(ReplyKeyPojo.class),plat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		replyMap.clear();
		for (ReplyKeyPojo replyKey : list) {
			String command = replyKey.getCommand();
			replyMap.put(command, replyKey);
			String keyworld = replyKey.getKeyworld();
			if (StringUtils.isNotEmpty(keyworld)) {
				String[] keyworlds = keyworld.split(",");
				for (String keyw : keyworlds) {
					replyMap.put(keyw, replyKey);
				}
			}
		}
		
		ReplyKeyPojo replyKey = replyMap.get(content);
		return replyKey;
		
	}
	

}
