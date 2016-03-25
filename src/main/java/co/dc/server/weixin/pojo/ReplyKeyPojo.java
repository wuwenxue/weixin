package co.dc.server.weixin.pojo;

import java.io.Serializable;


public class ReplyKeyPojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6720070437426941216L;

	private Long id;
	
	private String name;
	//数字指令
	private String command;
	
	//关键字逗号隔开
	private String keyworld;
	
	//文字回复内容
	private String comment;
	
	//0文字 1图文 2内置图文  3图片
	private int replyType;
	
	private Long materialId;
	
	private String materialName;
	
	private String mediaId;

	//是否是洗车店
	private String washis;
	
	private String inlayId;
	
	public Long getId() {
		return id;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getKeyworld() {
		return keyworld;
	}

	public void setKeyworld(String keyworld) {
		this.keyworld = keyworld;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getReplyType() {
		return replyType;
	}

	public void setReplyType(int replyType) {
		this.replyType = replyType;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		
		
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWashis() {
		return washis;
	}

	public void setWashis(String washis) {
		this.washis = washis;
	}

	public String getInlayId() {
		return inlayId;
	}

	public void setInlayId(String inlayId) {
		this.inlayId = inlayId;
	}
	
	
	
}
