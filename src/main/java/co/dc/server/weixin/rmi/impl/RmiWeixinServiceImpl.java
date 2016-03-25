package co.dc.server.weixin.rmi.impl;

import java.util.UUID;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import co.dc.commons.dto.RetStatus;
import co.dc.commons.platform.PlatformConstant;
import co.dc.server.member.dto.MemberDto;
import co.dc.server.member.rmi.RmiMemberServer;
import co.dc.server.weixin.api.JiayouWeiXinManage;
import co.dc.server.weixin.api.TikiboxWeiXinManage;
import co.dc.server.weixin.rmi.RmiWeixinService;
import co.dc.server.weixin.rmi.TempQrDto;

@Service("rmiWeixinService")
public class RmiWeixinServiceImpl implements RmiWeixinService{

	@Resource
	private TikiboxWeiXinManage tikiboxWeiXinManage;
	@Resource
	private JiayouWeiXinManage jiayouWeiXinManage;
	@Resource
	private RmiMemberServer rmiMemberServer;
	@Resource(name="tempQrCache")
	private Cache tempQrCache;
	@Resource(name="couponTemQrCache")
	private Cache couponTemQrCache;
	
	private static final Log log = LogFactory.getLog(RmiWeixinServiceImpl.class);
	
	public RetStatus<TempQrDto> buildLoginTempQr() {
		
		RetStatus<TempQrDto> ret = new RetStatus<TempQrDto>(1,"生成二维码失败");
		try{
			TempQrDto tempQrDto = tikiboxWeiXinManage.createTempQr(120, 665);
			tempQrDto.setAction("login");
			ret.set(0, "生成成功",tempQrDto);
			tempQrCache.put(new Element(tempQrDto.getTicket(), tempQrDto));
			tempQrCache.put(new Element(tempQrDto.getContent(), tempQrDto));
		}catch(Exception e){
			log.error("生成登录二维码异常",e);
		}
		
		return ret;
	}

	public RetStatus<MemberDto> qrIsLogin(String serial) {
		log.info("qrIsLogin收到调用请求 "+serial);
		RetStatus<MemberDto> ret = new RetStatus<MemberDto>(1,"请扫码");
		
		Element cacheElement = tempQrCache.get(serial);
		
		if(cacheElement!=null){
			TempQrDto tempQrDto = (TempQrDto)cacheElement.getObjectValue();
			if(tempQrDto.getAction().equals("login")){
				String status = tempQrDto.getStatus();
				if(status.equals("ok")){
					
					MemberDto memberDto = rmiMemberServer.loginByWid(tempQrDto.getWid(),PlatformConstant.TIKIBOX_WEB.getVal());
					
					ret.set(0, "登录成功",memberDto);
				}
			}
		}
		return ret;
	}

	public RetStatus<MemberDto> getMemberByCode(String code,String plat) {
		
		RetStatus<MemberDto> ret = new RetStatus<MemberDto>(1,"未取到用户信息");
		
		String wid = "";
		if(PlatformConstant.TIKIBOX_WEB.getVal().equals(plat)){
			wid = tikiboxWeiXinManage.getCodeAccessToken(code);
		}else if(PlatformConstant.JIAYOU_WEB.getVal().equals(plat)){
			wid = jiayouWeiXinManage.getCodeAccessToken(code);
		}
		if(StringUtils.isNotEmpty(wid)){
			MemberDto memberDto = rmiMemberServer.loginByWid(wid,plat);
			ret.set(0, "获取成功",memberDto);
		}
		return ret;
	}

	public RetStatus<TempQrDto> couponTempQr() {
		RetStatus<TempQrDto> ret = new RetStatus<TempQrDto>(1,"生成失败");
		try {
			TempQrDto dto = jiayouWeiXinManage.createTempQr(120, 667);
			dto.setAction("coupon");
			ret.set(0, "成功", dto);
			
			couponTemQrCache.put(new Element(dto.getTicket(), dto));
			couponTemQrCache.put(new Element(dto.getContent(), dto));
		} catch (Exception e) {
			log.error("生产优惠券使用临时二维码异常", e);
			ret.set(-1, "生成失败");
		}
		return ret;
	}

	public RetStatus<String> qrIscoupon(String serial,long codeId) {
		log.info("qrIscoupon收到调用请求 "+serial);
		RetStatus<String> ret = new RetStatus<String>(10,"请扫码");
		Element cacheElement = couponTemQrCache.get(serial);
		if(cacheElement!=null){
			TempQrDto tempQrDto = (TempQrDto)cacheElement.getObjectValue();
			if(tempQrDto.getAction().equals("coupon")){
				String status = tempQrDto.getStatus();
				System.out.println(status);
				if(status.equals("ok")){
					System.out.println("扫描成功"+status);
					ret= rmiMemberServer.useCoupon(codeId);
				}
			}
		}
		return ret;
	}

}
