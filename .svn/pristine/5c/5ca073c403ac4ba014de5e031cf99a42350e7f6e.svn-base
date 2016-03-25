package co.dc.server.weixin.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import co.dc.commons.basedao.BaseDao;
import co.dc.commons.dto.RetStatus;
import co.dc.commons.page.PageConstant;
import co.dc.commons.page.PageValItem;
import co.dc.commons.page.QueryResult;
import co.dc.server.weixin.pojo.AttentionPojo;
import co.dc.server.weixin.pojo.SenderToWeixinPojo;
import co.dc.server.weixin.pojo.WeixinToSenderPojo;

@Service("weixinToSenderService")
public class WeixinToSenderService extends BaseDao<WeixinToSenderPojo> {
	private static final Log log = LogFactory.getLog(WeixinToSenderService.class);
	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return WeixinToSenderPojo.tableName;
	}
	
	//分页查询
		public QueryResult<WeixinToSenderPojo> pageList(List<PageValItem> whereList,int pageindex, int maxsize) {
			QueryResult<WeixinToSenderPojo> queryResult = new QueryResult<WeixinToSenderPojo>();
			List<PageValItem> orderList = new ArrayList<PageValItem>();
			try {
				orderList.add(new PageValItem(PageConstant.DESC,"createDate"));
				queryResult = pageListByParam(pageindex, maxsize, whereList, orderList);
			} catch (Exception e) {
				log.error("pageList error", e);
			}
			return queryResult;
		}
		
		
		//收集微信发出的信息(添加方法)
		public RetStatus<WeixinToSenderPojo> addWeixinToSenderInfo(String toUserName,String fromUserName,String msgType,String content){
			RetStatus<WeixinToSenderPojo> retStatus = new RetStatus<WeixinToSenderPojo>(0,"添加成功");
			
			WeixinToSenderPojo weixinToSenderPojo = new WeixinToSenderPojo();
			weixinToSenderPojo.setToUserName(toUserName);
			weixinToSenderPojo.setFromUserName(fromUserName);
			weixinToSenderPojo.setMsgType(msgType);
			weixinToSenderPojo.setContent(content);
		
			try {
				save(weixinToSenderPojo);
			} catch (Exception e) {
				log.error("收集微信信息失败", e);
				retStatus.set(-1, "收集微信发送信息失败!");
			}
			return retStatus;
			
		}
		
		
		//删除
		public RetStatus<WeixinToSenderPojo> delWeixinToSenderInfo(long id){
			RetStatus<WeixinToSenderPojo> retStatus = new RetStatus<WeixinToSenderPojo>(0,"删除成功");
			
			try {
				del(id);
			} catch (Exception e) {
				log.error("删除信息失败", e);
				retStatus.set(-1, "删除信息失败，请稍后再试!");
			}
			return retStatus;
		}

}
