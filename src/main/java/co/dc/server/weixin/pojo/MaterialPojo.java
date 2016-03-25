package co.dc.server.weixin.pojo;

import java.io.Serializable;

public class MaterialPojo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7970843248905620463L;

	private Long id;
	
	private String name;
	
	private String xml;
	//tuwen  wenzi
	private String type;
	
	private String plat;
	
	private String createtime;
	
	public String getPlat() {
		return plat;
	}
	public void setPlat(String plat) {
		this.plat = plat;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	

}
