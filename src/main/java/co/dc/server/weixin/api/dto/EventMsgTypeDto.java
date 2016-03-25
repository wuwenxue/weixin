package co.dc.server.weixin.api.dto;

public class EventMsgTypeDto extends BaseMsgDto{
	//事件类型 subscribe订阅  unsubscribe取消订阅 SCAN LOCATION CLICK
	private String event;
	private String eventKey;//事件KEY值，qrscene_为前缀，后面为二维码的参数值
	private String ticket;//二维码的ticket，可用来换取二维码图片
	
	private String latitude;
	private String longitude;
	private String precision;
	
	private String scanCodeInfo;
	private String scanType;
	private String scanResult;
	
	
	
	
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getScanCodeInfo() {
		return scanCodeInfo;
	}
	public void setScanCodeInfo(String scanCodeInfo) {
		this.scanCodeInfo = scanCodeInfo;
	}
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public String getScanResult() {
		return scanResult;
	}
	public void setScanResult(String scanResult) {
		this.scanResult = scanResult;
	}
	
	
	
}
