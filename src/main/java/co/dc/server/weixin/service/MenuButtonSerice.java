package co.dc.server.weixin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import co.dc.commons.basedao.BaseDao;
import co.dc.commons.dto.RetStatus;
import co.dc.commons.page.PageConstant;
import co.dc.commons.page.PageValItem;
import co.dc.commons.page.QueryResult;
import co.dc.commons.platform.PlatformConstant;
import co.dc.commons.utils.DateTimeUtil;
import co.dc.server.weixin.pojo.MenuButtonPojo;

@Service
public class MenuButtonSerice extends BaseDao<MenuButtonPojo> {
	private static final Log log = LogFactory.getLog(MenuButtonSerice.class);

	protected String getTableName() {
		return MenuButtonPojo.tableName;
	}

	// @Resource
	// private WeiXinManage weiXinManage;

	public QueryResult<MenuButtonPojo> menuBarList(int pageindex, int maxsize,
			String plat) {
		List whereList = new ArrayList();
		PageValItem item = new PageValItem(PageConstant.EQ, "menuId", 0);
		PageValItem item2 = null;
		if (StringUtils.isNotEmpty(plat)) {
			if (plat.equals("jiayou")) {
				item2 = new PageValItem(PageConstant.EQ, "plat",
						PlatformConstant.JIAYOU_WEB.getVal());
				whereList.add(item2);
			} else if (plat.equals("tikibox")) {
				item2 = new PageValItem(PageConstant.EQ, "plat",
						PlatformConstant.TIKIBOX_WEB.getVal());
				whereList.add(item2);
			}
		}
		whereList.add(item);
		List orderList = new ArrayList();
		return pageListByParam(pageindex, maxsize, whereList, orderList);
	}

	public QueryResult<MenuButtonPojo> subMenuList(int pageindex, int maxsize,
			long id) {
		List whereList = new ArrayList();
		PageValItem item = new PageValItem(PageConstant.EQ, "menuId", id);
		whereList.add(item);
		List orderList = new ArrayList();
		return pageListByParam(pageindex, maxsize, whereList, orderList);
	}

	// 查询父菜单
	public List<MenuButtonPojo> menuList(String platform) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menuId", 0);
		map.put("plat", platform);
		return listByParam(map);
	}

	// 根据Id查询子菜单
	public List<MenuButtonPojo> menuIdList(long id) {
		return listByParam("menuId", id);
	}

	// 查询所有菜单数量
	public long menuAllCount() {
		return list().size();
	}

	public RetStatus saveMenu(String name, String type, String key, String url,
			long menuId, String plat) {
		RetStatus ret = new RetStatus(0, "添加成功");
		try {
			MenuButtonPojo buttonPojo = new MenuButtonPojo();
			if (StringUtils.isEmpty(plat)) {
				ret.set(4, "未知平台");
				return ret;
			}
			if (plat.equals("jiayou")) {
				plat = PlatformConstant.JIAYOU_WEB.getVal();
			} else if (plat.equals("tikibox")) {
				plat = PlatformConstant.TIKIBOX_WEB.getVal();
			} else {
				ret.set(4, "未知平台");
				return ret;
			}
			if (menuId == 0) {
				int menuCount = menuList(plat).size();
				if (menuCount >= 3) {
					ret.set(1, "父菜单最多3个！");
					return ret;
				}
				
			} else {
				MenuButtonPojo buttonPojo2 = findByParam("id", menuId);
				if (buttonPojo2.getType().equals("view")) {
					ret.set(3, "view类型下不能存在子菜单");
					return ret;
				}
				if(StringUtils.isNotEmpty(buttonPojo2.getKeyMenu())){
					ret.set(8, "所需父类存在接口推送，不能有子类");
					return ret;
				}
				int menuCount = menuIdList(menuId).size();
				if (menuCount >= 5) {
					ret.set(2, "子菜单最多存在5个");
					return ret;
				}
			}
			buttonPojo.setName(name);
			buttonPojo.setType(type);
			if (type.equals("view")) {
				if (StringUtils.isEmpty(url)) {
					ret.set(5, "请输入URl地址");
				}
				buttonPojo.setUrl(url);
			} else {
				if (StringUtils.isNotEmpty(key)) {
					buttonPojo.setKeyMenu(key);
				}
			}
			buttonPojo.setCreateDate(DateTimeUtil.getDateTimeString());
			buttonPojo.setMenuId(menuId);
			buttonPojo.setPlat(plat);
			save(buttonPojo);
		} catch (Exception e) {
			ret.set(-1, "添加失败");
			log.error("添加微信菜单异常", e);
		}
		return ret;
	}

	public RetStatus updateMenu(long id, String name, String type, String key,
			String url, long menuId) {
		RetStatus ret = new RetStatus(0, "修改成功");
		try {
			MenuButtonPojo buttonPojo = (MenuButtonPojo) findById(id);
			if (menuId > 0) {
				MenuButtonPojo buttonPojo2 = findById(menuId);
				if(StringUtils.isNotEmpty(buttonPojo2.getKeyMenu())){
					ret.set(9, "所需父类存在接口推送，不能有子类");
					return ret;
				}
				if (buttonPojo.getMenuId() > 0) {
					if (menuId != buttonPojo.getMenuId()) {
						long sub_menu = menuIdList(menuId).size();
						if (sub_menu >= 5) {
							ret.set(2, "选中父菜单下子菜单已满");// 此父类下子类已满
							return ret;
						}
					}
					
					
				} else {
					long menuCount = menuIdList(id).size();
					if (menuCount > 0) {
						ret.set(1, "当前父菜单有多个子菜单，不可在设为子菜单");// 子菜单下有子菜单不可在做子菜单
						return ret;
					}
					long sub_menu = menuIdList(menuId).size();
					if (sub_menu >= 5) {
						ret.set(2, "选中父菜单下子菜单已满");// 此父类下子类已满
						return ret;
					}
					
					
				}
			} else {
				long count = menuIdList(id).size();
				if(type.equals("view")){
					if(count>0){
						ret.set(11, "当前菜单下存在子菜单，不可设置为view类型");
						return ret;
					}
				}
				if(StringUtils.isNotEmpty(key)){
					if(count>0){
						ret.set(12, "当前菜单存在子菜单，不可设置为接口推送");
						return ret;
					}
				}
				if (buttonPojo.getMenuId() > 0) {
					long menu3 = menuList(buttonPojo.getPlat()).size();
					if (menu3 >= 3) {
						ret.set(3, "最多存在3个父类");// 父菜单已满
						return ret;
					}
				}
			}
			buttonPojo.setName(name);
			buttonPojo.setType(type);
			if (type.equals("view")) {
				if (StringUtils.isEmpty(url)) {
					ret.set(6, "请输入url");
					return ret;
				}
			}
			buttonPojo.setUrl(url);
			buttonPojo.setKeyMenu(key);
			buttonPojo.setMenuId(menuId);
			update(buttonPojo);
		} catch (Exception e) {
			ret.set(-1, "修改失败");
			log.error("修改菜单异常", e);
		}
		return ret;
	}

	public RetStatus delMenu(long id) {
		RetStatus ret = new RetStatus();
		try {
			int count = listByParam("menuId", id).size();
			if (count > 0) {
				int flag = del("menuId", id);
				if (flag == count) {
					del(id);
					ret.setStatus(0);
				}
			} else {
				del(id);
				ret.setStatus(0);
			}
		} catch (Exception e) {
			ret.set(-1, "删除失败");
			log.error("删除菜单异常", e);
		}
		return ret;
	}

	/*
	 * //菜单同步 public String syncMenu(){ try { long menuFlag = queryMenu();
	 * if(menuFlag>0){ String str = delMenu(); if(str.equals("ok")){ String msg
	 * = createMenu(); if(msg.equals("ok")){ return "0"; }else{ return "1"; }
	 * }else{ return "1"; } }else{ String msg = createMenu();
	 * if(msg.equals("ok")){ return "0"; }else{ return "1"; } } } catch
	 * (Exception e) { log.error("同步微信菜单异常", e); } return ""; }
	 */

	/*
	 * //盒子房拼接菜单 public String splitButton(String platform){
	 * List<MenuButtonPojo> list = menuList(platform); StringBuffer sb = new
	 * StringBuffer(); if(list.size()>0){ sb.append("{");
	 * sb.append("\"button\":["); for(int i=0;i<list.size();i++){ MenuButtonPojo
	 * buttonPojo = list.get(i); List<MenuButtonPojo> subList =
	 * menuIdList(buttonPojo.getId()); if(subList.size()<=0){ sb.append("{");
	 * if(StringUtils.isNotEmpty(buttonPojo.getType())){
	 * sb.append("\"type\":\""); sb.append(buttonPojo.getType());
	 * sb.append("\","); } if(StringUtils.isNotEmpty(buttonPojo.getName())){
	 * sb.append("\"name\":\""); sb.append(buttonPojo.getName());
	 * sb.append("\","); }
	 * 
	 * if(buttonPojo.getType().equals("view")){
	 * if(StringUtils.isNotEmpty(buttonPojo.getUrl())){ sb.append("\"url\":\"");
	 * sb.append(buttonPojo.getUrl()); sb.append("\""); } }else
	 * if(buttonPojo.getType().equals("location_select")){
	 * if(StringUtils.isNotEmpty(buttonPojo.getKeyMenu())){
	 * sb.append("\"key\":\""); sb.append(buttonPojo.getKeyMenu());
	 * sb.append("\""); } }else if(buttonPojo.getType().equals("click")){
	 * if(StringUtils.isNotEmpty(buttonPojo.getKeyMenu())){
	 * sb.append("\"key\":\""); sb.append(buttonPojo.getKeyMenu());
	 * sb.append("\""); } }else{
	 * if(StringUtils.isNotEmpty(buttonPojo.getKeyMenu())){
	 * sb.append("\"key\":\""); sb.append(buttonPojo.getKeyMenu());
	 * sb.append("\","); } sb.append("\"sub_button\":[]"); } sb.append("}");
	 * if((i+1)<list.size()){ sb.append(","); } }else{ sb.append("{");
	 * if(StringUtils.isNotEmpty(buttonPojo.getName())){
	 * sb.append("\"name\":\""); sb.append(buttonPojo.getName());
	 * sb.append("\","); } sb.append("\"sub_button\":["); for (int j = 0; j <
	 * subList.size(); j++) { MenuButtonPojo menuButtonPojo = subList.get(j);
	 * sb.append("{"); if(StringUtils.isNotEmpty(menuButtonPojo.getType())){
	 * sb.append("\"type\":\""); sb.append(menuButtonPojo.getType());
	 * sb.append("\","); } if(StringUtils.isNotEmpty(menuButtonPojo.getName())){
	 * sb.append("\"name\":\""); sb.append(menuButtonPojo.getName());
	 * sb.append("\","); } if(menuButtonPojo.getType().equals("view")){
	 * if(StringUtils.isNotEmpty(menuButtonPojo.getUrl())){
	 * sb.append("\"url\":\""); sb.append(menuButtonPojo.getUrl());
	 * sb.append("\""); } }else
	 * if(menuButtonPojo.getType().equals("location_select")){
	 * if(StringUtils.isNotEmpty(menuButtonPojo.getKeyMenu())){
	 * sb.append("\"key\":\""); sb.append(buttonPojo.getKeyMenu());
	 * sb.append("\""); } }else if(menuButtonPojo.getType().equals("click")){
	 * if(StringUtils.isNotEmpty(menuButtonPojo.getKeyMenu())){
	 * sb.append("\"key\":\""); sb.append(menuButtonPojo.getKeyMenu());
	 * sb.append("\""); } }else{
	 * if(StringUtils.isNotEmpty(menuButtonPojo.getKeyMenu())){
	 * sb.append("\"key\":\""); sb.append(menuButtonPojo.getKeyMenu());
	 * sb.append("\","); } sb.append("\"sub_button\":[]"); } sb.append("}");
	 * if((j+1)<subList.size()){ sb.append(","); } }
	 * 
	 * sb.append("]"); sb.append("}"); if((i+1)<list.size()){ sb.append(","); }
	 * } } sb.append("]"); sb.append("}"); } return sb.toString(); }
	 * 
	 * //拼接驾友网微信菜单 public String createButton(){ StringBuffer sb= new
	 * StringBuffer(); sb.append("{"); sb.append("\"button\":[");
	 * sb.append("{"); sb.append("\"type\":\"click\",");
	 * sb.append("\"name\":\"洗车点\","); sb.append("\"key\":\"4\"");
	 * sb.append("},{"); sb.append("\"name\":\"活动\",");
	 * sb.append("\"sub_button\":[{"); sb.append("\"type\":\"click\",");
	 * sb.append("\"name\":\"集分享\","); sb.append("\"key\":\"10\"");
	 * sb.append("}]},"); sb.append("{"); sb.append("\"type\":\"click\",");
	 * sb.append("\"name\":\"联系我们\","); sb.append("\"key\":\"30\"");
	 * sb.append("}]"); sb.append("}"); return sb.toString(); }
	 * 
	 * //创建菜单 public String createMenu(){
	 * 
	 * String accessToken = weiXinManage.getAccessToken(); String
	 * url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
	 * +accessToken; String msg=""; try { log.info("[开始创建微信菜单]");
	 * CloseableHttpClient client = HttpClientBuilder.create().build(); HttpPost
	 * httpPost = new HttpPost(url); httpPost.setEntity(new
	 * StringEntity(createButton(), "UTF-8")); HttpResponse response
	 * =client.execute(httpPost); String jsonStr =
	 * EntityUtils.toString(response.getEntity(), "utf-8"); JSONObject json =
	 * new JSONObject(jsonStr); msg = json.getString("errmsg").toString();
	 * log.info("[创建微信菜单返回值："+msg+"]"); } catch (ClientProtocolException e) {
	 * log.error("创建微信菜单异常", e); } catch (IOException e) { log.error("创建微信菜单异常",
	 * e); } catch (JSONException e) { log.error("创建微信菜单异常", e); } return msg; }
	 * 
	 * //查询菜单 public long queryMenu(){
	 * 
	 * String accessToken = weiXinManage.getAccessToken(); String
	 * url="https://api.weixin.qq.com/cgi-bin/menu/get?access_token="
	 * +accessToken; JSONArray array = new JSONArray(); try {
	 * log.info("[开始查询微信菜单]"); CloseableHttpClient client =
	 * HttpClientBuilder.create().build(); HttpGet httpGet = new HttpGet(url);
	 * HttpResponse response = client.execute(httpGet); String jsonStr =
	 * EntityUtils.toString(response.getEntity(),"utf-8");
	 * if(!jsonStr.contains("errcode")){ JSONObject json = new
	 * JSONObject(jsonStr); JSONObject jsonMenu = json.getJSONObject("menu");
	 * array = jsonMenu.getJSONArray("button"); } log.info("[结算查询微信菜单]");
	 * log.info("[查询微信返回长度"+array.length()+"]"); } catch
	 * (ClientProtocolException e) { log.error("查询微信菜单异常", e); } catch
	 * (IOException e) { log.error("查询微信菜单异常", e); } catch (JSONException e) {
	 * log.error("查询微信菜单异常", e); } return array.length(); }
	 * 
	 * //删除菜单 public String delMenu(){ String accessToken =
	 * weiXinManage.getAccessToken(); String
	 * url="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="
	 * +accessToken; String msg=""; try { log.info("[开始删除微信菜单]");
	 * CloseableHttpClient client = HttpClientBuilder.create().build(); HttpGet
	 * httpGet = new HttpGet(url); HttpResponse response =
	 * client.execute(httpGet); String jsonStr =
	 * EntityUtils.toString(response.getEntity(), "utf-8"); JSONObject json =
	 * new JSONObject(jsonStr); msg = json.getString("errmsg").toString();
	 * log.info("[删除微信菜单返回值："+msg+"]"); } catch (ClientProtocolException e) {
	 * log.error("删除微信菜单异常", e); } catch (IOException e) { log.error("删除微信菜单异常",
	 * e); } catch (JSONException e) { log.error("删除微信菜单异常", e); } return msg; }
	 */

}