package co.dc.server.weixin.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import co.dc.commons.json.JSONArray;
import co.dc.commons.json.JSONObject;


public class MaterialVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8640160545323436713L;
	private int num;
	private String title;
	private String materialPath;
	private String note;
	private String source;
	private String contentDesc;
	private String plat;

	public String getPlat() {
		return plat;
	}

	public void setPlat(String plat) {
		this.plat = plat;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMaterialPath() {
		return materialPath;
	}

	public void setMaterialPath(String materialPath) {
		this.materialPath = materialPath;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getContentDesc() {
		return contentDesc;
	}

	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}

	public static List<MaterialVo> convertList(String json) {
		
		List<MaterialVo> list = new ArrayList<MaterialVo>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				MaterialVo materialVo = new MaterialVo();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String contentDesc = jsonObject.getString("contentDesc");
				String materialPath = jsonObject.getString("materialPath");
				String note = jsonObject.getString("note");
				int num = jsonObject.getInt("num");
				String source = jsonObject.getString("source");
				String title = jsonObject.getString("title");
				materialVo.setContentDesc(contentDesc);
				materialVo.setMaterialPath(materialPath);
				materialVo.setNote(note);
				materialVo.setNum(num);
				materialVo.setSource(source);
				materialVo.setTitle(title);
				list.add(materialVo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/*<xml>
	<ToUserName><![CDATA[%s]]></ToUserName>
	<FromUserName><![CDATA[%s]]></FromUserName>
	<CreateTime>%s</CreateTime>
	<MsgType><![CDATA[news]]></MsgType>
	<ArticleCount>2</ArticleCount>
	<Articles>
	<item>
	<Title><![CDATA[title1]]></Title> 
	<Description><![CDATA[description1]]></Description>
	<PicUrl><![CDATA[picurl]]></PicUrl>
	<Url><![CDATA[url]]></Url>
	</item>
	<item>
	<Title><![CDATA[title]]></Title>
	<Description><![CDATA[description]]></Description>
	<PicUrl><![CDATA[picurl]]></PicUrl>
	<Url><![CDATA[url]]></Url>
	</item>
	</Articles>
	</xml> 
	*/
	public static String convertXml(String json,Long id){
		List<MaterialVo> list = convertList(json);
		StringBuffer sb = new StringBuffer("<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[news]]></MsgType>");
		sb.append("<ArticleCount>").append(list.size()).append("</ArticleCount>");
		sb.append("<Articles>");
		int flag = 0;
		for(MaterialVo materialVo : list){
			sb.append("<item>");
			sb.append("<Title>");
			sb.append("<![CDATA["+materialVo.getTitle()+"]]>");
			sb.append("</Title>");
			sb.append("<Description>");
			sb.append("<![CDATA["+materialVo.getNote()+"]]>");
			sb.append("</Description>");
			sb.append("<PicUrl>");
			if(StringUtils.isNotEmpty(materialVo.getMaterialPath())){
				sb.append("<![CDATA["+"http://www.jiayou.in/"+materialVo.getMaterialPath()+"]]>");
			}else{
				sb.append("<![CDATA[]]>");
			}
			
			sb.append("</PicUrl>");
			
			if(StringUtils.isEmpty(materialVo.getSource())){
				sb.append("<Url>");
				sb.append("<![CDATA["+"http://www.jiayou.in/jiayouwang/"+"weixinmaterial.htm?id="+id+"&num="+materialVo.getNum()+"]]>");
				sb.append("</Url>");
			}else{
				sb.append("<Url>");
				sb.append("<![CDATA["+materialVo.getSource()+"]]>");
				sb.append("</Url>");
			}
			sb.append("</item>");
			flag++;
		}
		
		sb.append("</Articles>");
		sb.append("</xml>");
		return sb.toString();
	}
	
	public static String imgXml(String obj){
		StringBuffer sb = new StringBuffer("<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[image]]></MsgType><Image>");
		sb.append(" <MediaId><![CDATA["+obj+"]]></MediaId>");
		sb.append(" </Image></xml>");
		return sb.toString();
	}
	

}
