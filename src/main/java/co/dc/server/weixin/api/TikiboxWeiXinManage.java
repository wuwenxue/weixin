package co.dc.server.weixin.api;

import java.io.IOException;
import java.util.List;
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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import co.dc.commons.json.JSONArray;
import co.dc.commons.json.JSONException;
import co.dc.commons.json.JSONObject;
import co.dc.commons.platform.PlatformConstant;
import co.dc.commons.utils.DateTimeUtil;
import co.dc.server.weixin.pojo.AttentionPojo;
import co.dc.server.weixin.pojo.MenuButtonPojo;
import co.dc.server.weixin.rmi.TempQrDto;
import co.dc.server.weixin.service.MenuButtonSerice;

@Service("tikiboxWeiXinManage")
public class TikiboxWeiXinManage extends WeiXinManage{

	@Resource
	private Configuration configuration;

	private String appId = "";
	private String appSecret = "";

	private String expiresTime = "";
	private String accessToken = "";
	
	private String platform = PlatformConstant.TIKIBOX_WEB.getVal();
	

	private static final Log log = LogFactory.getLog(TikiboxWeiXinManage.class);

	public String getAppId() {
		return configuration.getString("tikibox_wx_app_id");
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return configuration.getString("tikibox_wx_app_secret");
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
