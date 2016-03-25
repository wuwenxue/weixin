package co.dc.server.weixin.service;

import co.dc.commons.basedao.BaseDao;
import co.dc.commons.dto.RetStatus;
import co.dc.commons.json.JSONArray;
import co.dc.commons.json.JSONException;
import co.dc.commons.json.JSONObject;
import co.dc.commons.page.PageConstant;
import co.dc.commons.page.PageValItem;
import co.dc.commons.page.QueryResult;
import co.dc.commons.platform.PlatformConstant;
import co.dc.commons.utils.DateTimeUtil;
import co.dc.server.weixin.api.JiayouWeiXinManage;
import co.dc.server.weixin.api.TikiboxWeiXinManage;
import co.dc.server.weixin.api.WeiXinManage;
import co.dc.server.weixin.pojo.AttentionPojo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class AttentionService extends BaseDao<AttentionPojo> {
	private static final Log log = LogFactory.getLog(AttentionService.class);

	@Resource(name = "tikiboxWeiXinManage")
	private TikiboxWeiXinManage tikiboxWeiXinManage;

	@Resource(name = "jiayouWeiXinManage")
	private JiayouWeiXinManage jiayouWeiXinManage;

	protected String getTableName() {
		return AttentionPojo.tableName;
	}

	public QueryResult<AttentionPojo> listAttention(int pageindex, int maxsize,
			String plat) {
		List<PageValItem> whereList = new ArrayList<PageValItem>();
		if (StringUtils.isNotEmpty(plat)) {
			PageValItem item = new PageValItem(PageConstant.EQ, "plat", plat);
			whereList.add(item);
		}
		List orderList = null;
		return pageListByParam(pageindex, maxsize, whereList, orderList);
	}

	public RetStatus saveTikiboxAttention() {
		RetStatus ret = new RetStatus();
		try {
			RetStatus r = delAttention(PlatformConstant.TIKIBOX_WEB.getVal());
			JSONArray json = tikiboxWeiXinManage.getWeiAttention();
			String sql = "insert into "
					+ AttentionPojo.tableName
					+ " (wid,createDate,nickname,sex,address,headimgurl,subscribeTime,plat) values(?,?,?,?,?,?,?,?)";
			
			int j = 0;
			int g = 0;
			boolean flag = false;
			int batchInt = 50;
			Object[][] params = new Object[batchInt][8];
			for (int i = 0; i < json.length(); i++) {
				System.out.println(i);
				if (i % batchInt == 0 && i != 0) {
					queryRunner.batch(sql, params);
					j++;
					g = 0;
					if((j+1) * batchInt > json.length()){
						params = new Object[json.length() - i][8];
					}else{
						params = new Object[batchInt][8];
					}
				}
				if ( batchInt > json.length()) {
					if (flag == false) {
						params = new Object[json.length()][8];
						flag = true;
					}
				}

				AttentionPojo attentionPojo = tikiboxWeiXinManage
						.attentionInfo(json.get(i).toString());
				params[g][0] = attentionPojo.getWid();
				params[g][1] = DateTimeUtil.getDateTimeString();
				params[g][2] = attentionPojo.getNickname();
				params[g][3] = attentionPojo.getSex();
				params[g][4] = attentionPojo.getAddress();
				params[g][5] = attentionPojo.getHeadimgurl();
				params[g][6] = attentionPojo.getSubscribeTime();
				params[g][7] = PlatformConstant.TIKIBOX_WEB.getVal();

				g++;
			}
			if (params.length > 0) {
				queryRunner.batch(sql, params);
			}
			ret.setStatus(0);
		} catch (Exception e) {
			log.error("保存微信关注者列表异常", e);
			ret.set(-1, "保存失败");
		}
		return ret;
	}

	public RetStatus saveJiayouAttention() {
		RetStatus ret = new RetStatus();
		try {
			RetStatus r = delAttention(PlatformConstant.JIAYOU_WEB.getVal());
			JSONArray json = jiayouWeiXinManage.getWeiAttention();

			String sql = "insert into "
					+ getTableName()
					+ " (wid,createDate,nickname,sex,address,headimgurl,subscribeTime,plat) values(?,?,?,?,?,?,?,?)";

			Object[][] params = new Object[50][8];
			int j = 0;
			int g = 0;
			int batchInt=50;
			boolean flag = false;
			for (int i = 0; i < json.length(); i++) {
				System.out.println(i);
				if (i % 50 == 0 && i != 0) {
					
					queryRunner.batch(sql, params);
					j++;
					g = 0;
					if((j+1)*batchInt>json.length()){
						params = new Object[json.length()-i][8];
					}else{
						params = new Object[batchInt][8];
					}
				}
				if (batchInt > json.length()) {
					if(flag==false){
						params = new Object[json.length()][8];
						flag=true;
					}
				}

				AttentionPojo attentionPojo = jiayouWeiXinManage
						.attentionInfo(json.get(i).toString());
				params[g][0] = attentionPojo.getWid();
				params[g][1] = DateTimeUtil.getDateTimeString();
				params[g][2] = attentionPojo.getNickname();
				params[g][3] = attentionPojo.getSex();
				params[g][4] = attentionPojo.getAddress();
				params[g][5] = attentionPojo.getHeadimgurl();
				params[g][6] = attentionPojo.getSubscribeTime();
				params[g][7] = PlatformConstant.JIAYOU_WEB.getVal();

				g++;
			}
			if (params.length > 0) {
				queryRunner.batch(sql, params);
			}
			ret.setStatus(0);
		} catch (Exception e) {
			log.error("保存微信关注者列表异常", e);
			ret.set(-1, "保存失败");
		}
		return ret;
	}

	public RetStatus delAttention(String plat) {
		RetStatus ret = new RetStatus();
		try {
			String sql = "DELETE FROM " + getTableName() + " where plat=?";
			queryRunner.update(sql, plat);
			ret.setStatus(0);
		} catch (SQLException e) {
			ret.set(-1, "删除失败");
			log.error("删除所以关注者异常", e);
		}
		return ret;
	}

}