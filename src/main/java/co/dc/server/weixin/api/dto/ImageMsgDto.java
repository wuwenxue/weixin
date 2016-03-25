package co.dc.server.weixin.api.dto;

public class ImageMsgDto extends CustomMsgDto{

	
	private String picUrl;//图片连接
	private String mediaId;//图片消息媒体id
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	
}
