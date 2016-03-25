package co.dc.server.weixin.api;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import co.dc.commons.platform.PlatformConstant;

@Service("jiayouWeiXinManage")
public class JiayouWeiXinManage extends WeiXinManage {
	@Resource
	private Configuration configuration;
	
	private String appId = "";
	private String appSecret = "";

	private String expiresTime = "";
	private String accessToken = "";
	
	private String platform = PlatformConstant.JIAYOU_WEB.getVal();
	

	private static final Log log = LogFactory.getLog(JiayouWeiXinManage.class);

	public String getAppId() {
		return configuration.getString("jiayou_wx_app_id");
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return configuration.getString("jiayou_wx_app_secret");
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
