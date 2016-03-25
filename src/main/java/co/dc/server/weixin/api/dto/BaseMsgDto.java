package co.dc.server.weixin.api.dto;

public class BaseMsgDto {

	
	private String toUserName;//公共平台微信号
	private String fromUserName;//用户微信号
	private String CreateTime;//创建时间
	private String msgType;//消息类型
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	
	
	
	
	
	
}
