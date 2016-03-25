package co.dc.server.weixin.pojo;

import java.io.Serializable;

import co.dc.commons.annotation.Transient;
import co.dc.commons.utils.DateTimeUtil;

public class CardPojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4426471729647237938L;

	public static final String tableName = "card_card";

	private long id;

	private String name;

	private String head;

	// 0 全场工作证
	private int type;

	// 是否删除 0 否 1是
	private int delFlag = 0;

	private String createDate = DateTimeUtil.getDateTimeString();

	@Transient
	private String typeDesc;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTypeDesc() {
		if(type==0){
			return "全场工作证";
		}else if(type==1){
			return "内场工作证";
		}else if(type==2){
			return "现场销售证";
		}
		
		
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

}
