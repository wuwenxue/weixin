package co.dc.server.weixin.api.engine.jiayou;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import co.dc.commons.json.JSONObject;
import co.dc.commons.platform.PlatformConstant;
import co.dc.server.member.rmi.RmiMemberServer;
import co.dc.server.weixin.api.JiayouWeiXinManage;
import co.dc.server.weixin.api.WeiXinManage;
import co.dc.server.weixin.api.dto.BaseMsgDto;

@Service("jiayouEngineService")
public class JiayouEngineService {

	private static Log log = LogFactory.getLog(JiayouEngineService.class);
	
	@Resource
	private RmiMemberServer rmiMemberServer;
	@Resource
	private JiayouWeiXinManage jiayouWeiXinManage;
	@Resource
	private JiayouEventService jiayouEventService;
	@Resource
	private JiayouTextService jiayouTextService;

	private String plat = PlatformConstant.JIAYOU_WEB.getVal();
	
	private static final String RESPONSE_TXT = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Content><![CDATA[%s]]></Content><FuncFlag>0</FuncFlag></xml>";
	

	

	class MapKeyComparator implements Comparator<Double> {
		public int compare(Double str1, Double str2) {
			return str1.compareTo(str2);
		}
	}

	

	public String process(BaseMsgDto baseMsgDto,String xml) {
		String toUserName = "";
		String fromUserName = "";
		String content = "";
		String id = "";
		String msgType = "text";
		String event = "";
		String uid = "";
		Element root = null;
		try {
			Document doc = DocumentHelper.parseText(xml);
			root = doc.getRootElement();
			toUserName = root.element("ToUserName").getTextTrim();
			fromUserName = root.element("FromUserName").getTextTrim();
			msgType = root.element("MsgType").getTextTrim();
			if(msgType.equals("event")){
				event = root.element("Event").getTextTrim();
				if(event.equals("scancode_waitmsg")){
					String eventKey = root.element("EventKey").getTextTrim();
					Element scanCodeInfo = root.element("ScanCodeInfo");
					List<Element> list = scanCodeInfo.elements();
					Element scanType = list.get(0);
					if(scanType.getStringValue().equals("qrcode")){
						Element scanResult = list.get(1);
						content = scanResult.getStringValue();
						log.info("获取信息content："+content);
						if(StringUtils.isNotEmpty(content)){
							HttpPost httpPost = null;
							String str = "";
							int type = 0;
							
							WeiXinManage.getInstance().sendMessage(fromUserName, "正在查询请稍后...");
							
							if(eventKey.equals("1")){
								if(content.contains(",")){
									type = 0;//查询二维码返回经销商数据
									str= "{\"content\":\""+content+"\"}";
									httpPost = new HttpPost("http://180.96.23.218:8080/budweiserCoreNew/getAgencyNameByContent.htm");
								}else{
									type = 1;//查询垛号返回经销商
									httpPost = new HttpPost("http://180.96.23.218:8080/budweiserCoreNew/getAgencyNameByStacknum.htm");
									str= "{\"stackNum\":\""+content+"\"}";
								}
							}else{
								type = 2;//查询二维码垛号返回垛号信息
								str= "{\"content\":\""+content+"\"}";
								httpPost = new HttpPost("http://180.96.23.218:8080/budweiserCoreNew/getStackByContent.htm");
							}
							
							List formParams = new ArrayList();
							formParams.add(new BasicNameValuePair("json", str));
							HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
							httpPost.setEntity(entity);
							CloseableHttpClient client = HttpClientBuilder.create().build();
							HttpResponse response =client.execute(httpPost);
							String jsonStr = EntityUtils.toString(response.getEntity(),"UTF-8");
							if(StringUtils.isNotEmpty(jsonStr)){
								JSONObject jsonObject = new JSONObject(jsonStr);
								String agencyAdress = jsonObject.getString("agencyAdress");
								String agencyName = jsonObject.getString("agencyName");
								String province = jsonObject.getString("province");
								
								
								
								if(type==0){
									
									WeiXinManage.getInstance().sendMessage(fromUserName, "名称："+agencyName+"\n"+"地址："+agencyAdress+"\n"+"所属地区："+province);
									
								}else if(type==1){
									
									WeiXinManage.getInstance().sendMessage(fromUserName, "名称："+agencyName+"\n"+"地址："+agencyAdress+"\n"+"所属地区："+province);

								}else if(type ==2){
									
									WeiXinManage.getInstance().sendMessage(fromUserName, "名称："+agencyName+"\n"+"地址："+agencyAdress+"\n"+"所属地区："+province);

								}
								
										
							}else{
								
								WeiXinManage.getInstance().sendMessage(fromUserName, "没有相关联的数据！");
							}
						}
						
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}
