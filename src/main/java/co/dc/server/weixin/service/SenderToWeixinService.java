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

@Service("senderToWeixinService")
public class SenderToWeixinService extends BaseDao<SenderToWeixinPojo>{
	private static final Log log = LogFactory.getLog(SenderToWeixinService.class);
	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return SenderToWeixinPojo.tableName;
	}

	//分页查询
	public QueryResult<SenderToWeixinPojo> pageList(List<PageValItem> whereList,int pageindex, int maxsize) {
		QueryResult<SenderToWeixinPojo> queryResult = new QueryResult<SenderToWeixinPojo>();
		List<PageValItem> orderList = new ArrayList<PageValItem>();
		try {
			orderList.add(new PageValItem(PageConstant.DESC,"createDate"));
			queryResult = pageListByParam(pageindex, maxsize, whereList, orderList);
		} catch (Exception e) {
			log.error("pageList error", e);
		}
		return queryResult;
	}
	
	//收集发送者信息(添加方法)
	public RetStatus<SenderToWeixinPojo> addSenderInfo(AttentionPojo attentionPojo,String toUserName,String fromUserName,String msgType,String content){
		RetStatus<SenderToWeixinPojo> retStatus = new RetStatus<SenderToWeixinPojo>(0,"添加成功");
		
		SenderToWeixinPojo senderToWeixinPojo = new SenderToWeixinPojo();
		senderToWeixinPojo.setToUserName(toUserName);
		senderToWeixinPojo.setFromUserName(fromUserName);
		senderToWeixinPojo.setMsgType(msgType);
		senderToWeixinPojo.setContent(content);
		senderToWeixinPojo.setSenderId(attentionPojo.getWid());
		senderToWeixinPojo.setUseNickname(attentionPojo.getNickname());
		senderToWeixinPojo.setUseSex(attentionPojo.getSex());
		senderToWeixinPojo.setUseHeadimgurl(attentionPojo.getHeadimgurl());
		senderToWeixinPojo.setUseAddress(attentionPojo.getAddress());
		try {
			save(senderToWeixinPojo);
		} catch (Exception e) {
			log.error("添加发送者信息失败", e);
			retStatus.set(-1, "添加发送者信息失败!");
		}
		return retStatus;
		
	}
	
	//删除
	public RetStatus<SenderToWeixinPojo> delSenderToWeixinInfo(long id){
		RetStatus<SenderToWeixinPojo> retStatus = new RetStatus<SenderToWeixinPojo>(0,"删除成功");
		
		try {
			del(id);
		} catch (Exception e) {
			log.error("删除信息失败", e);
			retStatus.set(-1, "删除信息失败，请稍后再试!");
		}
		return retStatus;
	}
}
