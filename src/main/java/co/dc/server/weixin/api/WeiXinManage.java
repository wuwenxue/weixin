package co.dc.server.weixin.api;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import co.dc.commons.json.JSONArray;
import co.dc.commons.json.JSONException;
import co.dc.commons.json.JSONObject;
import co.dc.commons.utils.DateTimeUtil;
import co.dc.commons.utils.EmojiFilterUtils;
import co.dc.server.weixin.SSLSocketFactoryEx;
import co.dc.server.weixin.pojo.AttentionPojo;
import co.dc.server.weixin.pojo.MenuButtonPojo;
import co.dc.server.weixin.rmi.TempQrDto;
import co.dc.server.weixin.service.MenuButtonSerice;

public class WeiXinManage {

	@Resource
	private Configuration configuration;

	private String appId = "";
	private String appSecret = "";

	private String expiresTime = "";
	private String accessToken = "";
	
	private String platform = "";
	
	@Resource
	private MenuButtonSerice menuButtonSerice;
	private static WeiXinManage instance = null;
	public static synchronized WeiXinManage getInstance(){
		if(instance==null){
			instance = new WeiXinManage();
		}
		return instance;
	}

	private static final Log log = LogFactory.getLog(WeiXinManage.class);

	public String getTempAccessToken() {
		if ((StringUtils.isNotEmpty(getExpiresTime()))
				&& (StringUtils.isNotEmpty(getAccessToken()))) {
			int _ret = DateTimeUtil.compareMinute(
					DateTimeUtil.getDateTimeString(), getExpiresTime());
			if (_ret < 0) {
				return getAccessToken();
			}
		}
		HttpClient httpclient = SSLSocketFactoryEx.getNewHttpClient();

		HttpGet request = new HttpGet(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
						+ getAppId() + "&secret=" + getAppSecret());
		try {
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String content = IOUtils.toString(entity.getContent(), "UTF-8");
				JSONObject jo = new JSONObject(content);
				setExpiresTime(DateTimeUtil.getNextDateTimeStringBySecond(6000));
				setAccessToken(jo.getString("access_token"));
				return getAccessToken();
			}
		} catch (Exception localException) {
		}
		return null;
	}

	public String getCodeAccessToken(String code) {

		HttpClient httpclient = SSLSocketFactoryEx.getNewHttpClient();

		HttpGet request = new HttpGet(
				"https://api.weixin.qq.com/sns/oauth2/access_token?appid="
						+ getAppId() + "&secret=" + getAppSecret() + "&code="
						+ code + "&grant_type=authorization_code");
		try {
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String content = IOUtils.toString(entity.getContent(), "UTF-8");
				JSONObject jo = new JSONObject(content);
				return jo.getString("openid");
			}
		} catch (Exception e) {
			return null;
		}

		return "";
	}
	
	public Map<String, String> jsSignature(String appid,String appSecret,String url) {

		String jsapi_ticket = getJsTicket(appid, appSecret);
//		String url = "http://w7752.xicp.net/shareActition/index.html";
		Map<String, String> ret = sign(jsapi_ticket, url,appid);
		for (Map.Entry entry : ret.entrySet()) {
		}

		return ret;
	}

	public  Map<String, String> sign(String jsapi_ticket, String url,String appid) {
		Map<String, String> ret = new HashMap<String, String>();
		// String nonce_str = create_nonce_str();
		// String timestamp = create_timestamp();
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appid", appid);
		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
	//发送客服消息
	public void sendMessage(String openid,String message) {
		String accessToken = getTempAccessToken();
		String str = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
				+ accessToken;
		
		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(str);
			
			
			String json = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
			json = String.format(json, openid,message);
			StringEntity stringEntity = new StringEntity(json,"UTF-8");
			httppost.setEntity(stringEntity);

			HttpResponse response = httpclient.execute(httppost);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), "utf-8");
			System.out.println(jsonStr);
			

		} catch (Exception e) {
			log.error("发送客服消息异常", e);
		}
	}

	public String getJsTicket(String appid,String appSecret) {
		if ((StringUtils.isNotEmpty(getExpiresTime()))
				&& (StringUtils.isNotEmpty(getAccessToken()))) {
			int _ret = DateTimeUtil.compareMinute(
					DateTimeUtil.getDateTimeString(), getExpiresTime());
			if (_ret < 0) {
				return getAccessToken();
			}
		}
		HttpClient httpclient = SSLSocketFactoryEx.getNewHttpClient();

		HttpGet request = new HttpGet(
				"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
						+ getTempAccessToken(appid,appSecret) + "&type=jsapi");
		try {
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String content = IOUtils.toString(entity.getContent(), "UTF-8");
				JSONObject jo = new JSONObject(content);
				setExpiresTime(DateTimeUtil
						.getNextDateTimeStringBySecond(6000));
				setAccessToken(jo.getString("ticket"));
				return getAccessToken();
			}
		} catch (Exception localException) {
		}
		return null;

	}

	public String getTempAccessToken(String appid,String appSecret) {
		if ((StringUtils.isNotEmpty(getExpiresTime()))
				&& (StringUtils.isNotEmpty(getAccessToken()))) {
			int _ret = DateTimeUtil.compareMinute(
					DateTimeUtil.getDateTimeString(), getExpiresTime());
			if (_ret < 0) {
				return getAccessToken();
			}
		}
		HttpClient httpclient = SSLSocketFactoryEx.getNewHttpClient();

		HttpGet request = new HttpGet(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
						+ appid + "&secret=" + appSecret);
		try {
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String content = IOUtils.toString(entity.getContent(), "UTF-8");
				JSONObject jo = new JSONObject(content);
				setExpiresTime(DateTimeUtil.getNextDateTimeStringBySecond(6000));
				setAccessToken(jo.getString("access_token"));
				return getAccessToken();
			}
		} catch (Exception localException) {
		}
		return null;
	}

	public String getCodeAccessToken(String code,String appid,String appSecret) {

		HttpClient httpclient = SSLSocketFactoryEx.getNewHttpClient();

		HttpGet request = new HttpGet(
				"https://api.weixin.qq.com/sns/oauth2/access_token?appid="
						+ appid + "&secret=" + appSecret + "&code="
						+ code + "&grant_type=authorization_code");
		try {
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String content = IOUtils.toString(entity.getContent(), "UTF-8");
				JSONObject jo = new JSONObject(content);
				return jo.getString("openid");
			}
		} catch (Exception e) {
			return null;
		}

		return "";
	}
	public String[] getAccessTokens(String code,String appid,String appSecret) {

		String[] ret = new String[2];
		HttpClient httpclient = SSLSocketFactoryEx.getNewHttpClient();

		HttpGet request = new HttpGet(
				"https://api.weixin.qq.com/sns/oauth2/access_token?appid="
						+ appid + "&secret=" + appSecret + "&code="
						+ code + "&grant_type=authorization_code");
		try {
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String content = IOUtils.toString(entity.getContent(), "UTF-8");
				JSONObject jo = new JSONObject(content);
				ret[0] = jo.getString("access_token");
				ret[1] = jo.getString("openid");
			}
		} catch (Exception e) {
			return null;
		}

		return ret;
	}
	
	public String[] getUserInfo(String code,String appid,String appSecret ){
		String[] result = new String[2];
		HttpClient httpclient = SSLSocketFactoryEx.getNewHttpClient();
		String[] ret = getAccessTokens(code, appid, appSecret);
		HttpGet request = new HttpGet(
				"https://api.weixin.qq.com/sns/userinfo?access_token="+ret[0]+"&openid="+ret[1]+"&lang=zh_CN");
		try {
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String content = IOUtils.toString(entity.getContent(), "UTF-8");
				JSONObject jo = new JSONObject(content);
				result[0] =jo.getString("nickname");
				result[1] =jo.getString("openid");
				return result;
			}
		} catch (Exception e) {
			return null;
		}

		return result;
	}

	public static String createSHA1Sign(SortedMap<String, String> signParams)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		Set es = signParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + v + "&");
		}

		String params = sb.substring(0, sb.lastIndexOf("&"));
		return getSha1(params);
	}

	public static String getSha1(String str) {
		if ((str == null) || (str.length() == 0)) {
			return null;
		}
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				buf[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(buf);
		} catch (Exception e) {
		}
		return null;
	}

	// 获取关注者列表
	public JSONArray getWeiAttention() {
		String accessToken = getTempAccessToken();
		JSONArray jsonArray = new JSONArray();
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="
					+ accessToken + "&next_openid=";
			HttpClient httpclient = SSLSocketFactoryEx.getNewHttpClient();
			HttpPost post = new HttpPost(url);
			HttpResponse response = httpclient.execute(post);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), "utf-8");
			JSONObject jsonObject = new JSONObject(jsonStr);
			JSONObject jsonOb = jsonObject.getJSONObject("data");
			jsonArray = jsonOb.getJSONArray("openid");
		} catch (ClientProtocolException e) {
			log.error("获取微信关注者列表中HttpClient异常", e);
		} catch (IOException e) {
			log.error("获取微信关注者列表中InputStream异常", e);
		} catch (JSONException e) {
			log.error("获取微信关注者列表中JSON异常", e);
		}
		return jsonArray;
	}
	
	
	//创建临时二维码
	public TempQrDto createTempQr(int expireSeconds,int scene_id) {
		TempQrDto tempQrDto = new TempQrDto();
		String accessToken = getTempAccessToken();
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken;
			HttpClient httpclient = SSLSocketFactoryEx.getNewHttpClient();
			HttpPost post = new HttpPost(url);
			post.setEntity(new StringEntity("{\"expire_seconds\": "+expireSeconds+", \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+scene_id+"}}}"));
			HttpResponse response = httpclient.execute(post);
			String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			JSONObject jsonObject = new JSONObject(jsonStr);
			tempQrDto.setTicket(jsonObject.getString("ticket"));
			tempQrDto.setExpireSeconds(jsonObject.getInt("expire_seconds"));
			tempQrDto.setQrUrl("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+tempQrDto.getTicket());
			tempQrDto.setContent(jsonObject.getString("url"));
			tempQrDto.setSerial(jsonObject.getString("ticket"));
		} catch (Exception e) {
			log.error("生成二维码异常", e);
		}
		return tempQrDto;
	}


	// 获取关注者基本信息
	public AttentionPojo attentionInfo(String wid) {
		log.info("开始获取["+wid+"]用户信息");
		AttentionPojo attentionPojo = new AttentionPojo();
		try {
			String accessToken = getTempAccessToken();
			String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
					+ accessToken + "&openid=" + wid + "&lang=zh_CN";
			HttpClient httpclient = SSLSocketFactoryEx.getNewHttpClient();
			HttpPost post = new HttpPost(url);
			HttpResponse response = httpclient.execute(post);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), "utf-8");
			JSONObject jsonObject = new JSONObject(jsonStr);
			if(!jsonStr.contains("errcode")){
				attentionPojo.setWid(wid);
				
				attentionPojo.setNickname(jsonObject.getString("nickname")
						.toString());
					
				String nickName = attentionPojo.getNickname();
				String _nickName = EmojiFilterUtils.filterEmoji(nickName);
				attentionPojo.setNickname(_nickName);
						
				attentionPojo.setSex(Integer.parseInt(jsonObject.getString("sex")
						.toString()));
				attentionPojo.setAddress(jsonObject.getString("country").toString()
						+ jsonObject.getString("province").toString()
						+ jsonObject.getString("city").toString());
				attentionPojo.setHeadimgurl(jsonObject.getString("headimgurl")
						.toString());
				attentionPojo.setSubscribeTime(jsonObject.getString(
						"subscribe_time").toString());
			}
		} catch (Exception e) {
			log.error("获取关注者基本信息异常", e);
		}
		return attentionPojo;
	}
	
	
	//菜单同步
		public String syncMenu(){
			try {
				long menuFlag = queryMenu();
				if(menuFlag>0){
					String str = delMenu();
					if(str.equals("ok")){
						String msg = createMenu();
						if(msg.equals("ok")){
							return "0";
						}else{
							return "1";
						}
					}else{
						return "1";
					}
				}else{
					String msg = createMenu();
					if(msg.equals("ok")){
						return "0";
					}else{
						return "1";
					}
				}
			} catch (Exception e) {
				log.error("同步微信菜单异常", e);
			}
			return "";
		}

		//拼接菜单
		public String splitButton(){
			System.out.println(getPlatform());
			List<MenuButtonPojo> list = menuButtonSerice.menuList(getPlatform());
			StringBuffer sb = new StringBuffer();
			if(list.size()>0){
				sb.append("{");
				sb.append("\"button\":[");
				for(int i=0;i<list.size();i++){
					MenuButtonPojo buttonPojo = list.get(i);
					List<MenuButtonPojo> subList = menuButtonSerice.menuIdList(buttonPojo.getId());
					if(subList.size()<=0){
						sb.append("{");
						if(StringUtils.isNotEmpty(buttonPojo.getType())){
							sb.append("\"type\":\"");
							sb.append(buttonPojo.getType());
							sb.append("\",");
						}
						if(StringUtils.isNotEmpty(buttonPojo.getName())){
							sb.append("\"name\":\"");
							sb.append(buttonPojo.getName());
							sb.append("\",");
						}
						
						if(buttonPojo.getType().equals("view")){
							if(StringUtils.isNotEmpty(buttonPojo.getUrl())){
								sb.append("\"url\":\"");
								sb.append(buttonPojo.getUrl());
								sb.append("\"");
							}
						}else if(buttonPojo.getType().equals("location_select")){
							if(StringUtils.isNotEmpty(buttonPojo.getKeyMenu())){
								sb.append("\"key\":\"");
								sb.append(buttonPojo.getKeyMenu());
								sb.append("\"");
							}
						}else if(buttonPojo.getType().equals("click")){
							if(StringUtils.isNotEmpty(buttonPojo.getKeyMenu())){
								sb.append("\"key\":\"");
								sb.append(buttonPojo.getKeyMenu());
								sb.append("\"");
							}
						}else{
							if(StringUtils.isNotEmpty(buttonPojo.getKeyMenu())){
								sb.append("\"key\":\"");
								sb.append(buttonPojo.getKeyMenu());
								sb.append("\",");
							}
							sb.append("\"sub_button\":[]");
						}
						sb.append("}");
						if((i+1)<list.size()){
							sb.append(",");
						}
					}else{
						sb.append("{");
						if(StringUtils.isNotEmpty(buttonPojo.getName())){
							sb.append("\"name\":\"");
							sb.append(buttonPojo.getName());
							sb.append("\",");
						}
						sb.append("\"sub_button\":[");	
						for (int j = 0; j < subList.size(); j++) {
							MenuButtonPojo menuButtonPojo = subList.get(j);
							sb.append("{");
								if(StringUtils.isNotEmpty(menuButtonPojo.getType())){
									sb.append("\"type\":\"");
									sb.append(menuButtonPojo.getType());
									sb.append("\",");
								}
								if(StringUtils.isNotEmpty(menuButtonPojo.getName())){
									sb.append("\"name\":\"");
									sb.append(menuButtonPojo.getName());
									sb.append("\",");
								}
								if(menuButtonPojo.getType().equals("view")){
									if(StringUtils.isNotEmpty(menuButtonPojo.getUrl())){
										sb.append("\"url\":\"");
										sb.append(menuButtonPojo.getUrl());
										sb.append("\"");
									}
								}else if(menuButtonPojo.getType().equals("location_select")){
									if(StringUtils.isNotEmpty(buttonPojo.getKeyMenu())){
										sb.append("\"key\":\"");
										sb.append(menuButtonPojo.getKeyMenu());
										sb.append("\"");
									}
								}else if(menuButtonPojo.getType().equals("click")){
									if(StringUtils.isNotEmpty(menuButtonPojo.getKeyMenu())){
										sb.append("\"key\":\"");
										sb.append(menuButtonPojo.getKeyMenu());
										sb.append("\"");
									}
								}else{
									if(StringUtils.isNotEmpty(menuButtonPojo.getKeyMenu())){
										sb.append("\"key\":\"");
										sb.append(menuButtonPojo.getKeyMenu());
										sb.append("\",");
									}
									sb.append("\"sub_button\":[]");
								}
								sb.append("}");
								if((j+1)<subList.size()){
									sb.append(",");
								}
						}
						
						sb.append("]");
						sb.append("}");
						if((i+1)<list.size()){
							sb.append(",");
						}
					}
				}
				sb.append("]");
				sb.append("}");
			}
			return sb.toString();
		}
		
		
		//创建菜单
		public String  createMenu(){
			StringBuffer sb = new StringBuffer();
			sb.append("{\"button\": [{");
			sb.append("\"name\":\"查询\",");
			sb.append("\"sub_button\":[");
			sb.append(" {\"type\": \"view\", \"name\": \"添加批次\",\"url\": \"http://jsdckj.chinacloudapp.cn/bwweixin/addBatch.html\"},");
			sb.append(" {\"type\": \"view\", \"name\": \"查看批次\",\"url\": \"http://jsdckj.chinacloudapp.cn/bwweixin/batchList.html\"},");
			sb.append(" {\"type\": \"scancode_waitmsg\", \"name\": \"扫描查经销商\",\"key\": \"1\",\"sub_button\": [ ]},");
			sb.append(" {\"type\": \"scancode_waitmsg\", \"name\": \"扫描查垛号\",\"key\": \"2\",\"sub_button\": [ ]}]},{");
			sb.append("\"name\":\"我\",");
			sb.append("\"sub_button\":[");
			sb.append(" {\"type\": \"click\", \"name\": \"帐号绑定\",\"key\": \"bd\"},");
			sb.append(" {\"type\": \"click\", \"name\": \"解绑\",\"key\": \"jb\"},");
			sb.append(" {\"type\": \"scancode_waitmsg\", \"name\": \"矩阵码验证\",\"key\": \"3\",\"sub_button\": [ ]}]}");
			
			String accessToken = getTempAccessToken();
			String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken;
			String msg="";
			try {
				log.info("[开始创建微信菜单]");
				CloseableHttpClient client = HttpClientBuilder.create().build();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new StringEntity(sb.toString(), "UTF-8"));
				HttpResponse response =client.execute(httpPost);
				String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8"); 
				JSONObject json = new JSONObject(jsonStr);
				msg = json.getString("errmsg").toString();
				log.info("[创建微信菜单返回值："+msg+"]");
			} catch (ClientProtocolException e) {
				log.error("创建微信菜单异常", e);
			} catch (IOException e) {
				log.error("创建微信菜单异常", e);
			} catch (JSONException e) {
				log.error("创建微信菜单异常", e);
			}
			return msg;
		}
		
		//查询菜单
		public long queryMenu(){
			
			String accessToken = getTempAccessToken();
			String url="https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+accessToken;
			JSONArray array = new JSONArray();
			try {
				log.info("[开始查询微信菜单]");
				CloseableHttpClient client = HttpClientBuilder.create().build();
				HttpGet httpGet = new HttpGet(url);
				HttpResponse response = client.execute(httpGet);
				String jsonStr = EntityUtils.toString(response.getEntity(),"utf-8");
				if(!jsonStr.contains("errcode")){
					JSONObject json = new JSONObject(jsonStr);
					JSONObject jsonMenu = json.getJSONObject("menu");
					array = jsonMenu.getJSONArray("button");
				}
				log.info("[结算查询微信菜单]");
				log.info("[查询微信返回长度"+array.length()+"]");
			} catch (ClientProtocolException e) {
				log.error("查询微信菜单异常", e);
			} catch (IOException e) {
				log.error("查询微信菜单异常", e);
			} catch (JSONException e) {
				log.error("查询微信菜单异常", e);
			}
			return array.length();
		}
		
		//删除菜单
		public String delMenu(){
			String accessToken = getTempAccessToken();
			String url="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+accessToken;
			String msg="";
			try {
				log.info("[开始删除微信菜单]");
				CloseableHttpClient client = HttpClientBuilder.create().build();
				HttpGet httpGet = new HttpGet(url);
				HttpResponse response = client.execute(httpGet);
				String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
				JSONObject json = new JSONObject(jsonStr);
				msg = json.getString("errmsg").toString();
				log.info("[删除微信菜单返回值："+msg+"]");
			} catch (ClientProtocolException e) {
				log.error("删除微信菜单异常", e);
			} catch (IOException e) {
				log.error("删除微信菜单异常", e);
			} catch (JSONException e) {
				log.error("删除微信菜单异常", e);
			}
			return msg;
		}
		
		//上传媒体
		public String uploadMedia(String url,String type){
			String accessToken = getTempAccessToken();
			String str = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type="+type;
			String msg="1";
			try {
				log.info("[开始上传媒体]");
				CloseableHttpClient client = HttpClientBuilder.create().build();
				HttpPost httpPost = new HttpPost(str);
				File file = new File(url);
				
				MultipartEntity multipartEntity = new MultipartEntity();
				FileBody body = new FileBody(file);
				multipartEntity.addPart("file", body);
				httpPost.setEntity(multipartEntity);
				HttpResponse response =client.execute(httpPost);
				String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8"); 
				JSONObject json = new JSONObject(jsonStr);
				if(!json.toString().contains("errcode")){
					msg = json.getString("media_id").toString();
					log.info("[上传媒体成功：media_id="+msg+"]");
				}else{
					log.info("[上传媒体失败]");
					log.info("[上传媒体返回值："+jsonStr+"]");
				}
				
			} catch (ClientProtocolException e) {
				log.error("上传媒体异常", e);
			} catch (IOException e) {
				log.error("上传媒体异常", e);
			} catch (JSONException e) {
				log.error("上传媒体异常", e);
			}
			return msg;
		}

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getAppSecret() {
			return appSecret;
		}

		public void setAppSecret(String appSecret) {
			this.appSecret = appSecret;
		}

		public String getExpiresTime() {
			return expiresTime;
		}

		public void setExpiresTime(String expiresTime) {
			this.expiresTime = expiresTime;
		}

		public String getPlatform() {
			return platform;
		}

		public void setPlatform(String platform) {
			this.platform = platform;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}
	
		public String getAccessToken() {
			return accessToken;
		}
	

}
