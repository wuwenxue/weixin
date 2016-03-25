package co.dc.server.weixin.api.dto;

public class VideoMsgDto extends CustomMsgDto{

	private String mediaId;//视频消息id
	private String thumbMediaId;//食品消息缩略图id
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
}
