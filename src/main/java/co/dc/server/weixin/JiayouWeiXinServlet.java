package co.dc.server.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import co.dc.server.weixin.api.dto.BaseMsgDto;
import co.dc.server.weixin.api.engine.jiayou.JiayouEngineService;
import co.dc.server.weixin.utils.XmlUtil;

public class JiayouWeiXinServlet  extends HttpServlet {

	private static final long serialVersionUID = -4267408236898837036L;

	
	private static final Log log = LogFactory.getLog(JiayouWeiXinServlet.class);
	private static final String TOKEN = "123456";

	private static final String RESPONSE_TXT = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Content><![CDATA[%s]]></Content><FuncFlag>0</FuncFlag></xml>";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		PrintWriter out = response.getWriter();
		if (checkSignature(signature, timestamp, nonce)) {
			out.print(request.getParameter("echostr"));
		}
		out.close();
		out = null;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		log.info("servlet 收到微信上行请求");
		WebApplicationContext wt = ContextLoader.getCurrentWebApplicationContext();
		JiayouEngineService jiayouEngineService = (JiayouEngineService)wt.getBean("jiayouEngineService");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		
		
		Document doc = null;
		SAXReader reader = new SAXReader();
		InputStream in = request.getInputStream();
		
		
		String xmlStr = IOUtils.toString(in, "UTF-8");
		BaseMsgDto baseMsgDto = XmlUtil.xmlStrToBean(xmlStr);
		
		String path = request.getContextPath()+"/";
		path = path.substring(1, path.length());
		// 获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是 http://localhost:8080/MyApp/）:  path  听见下雨的声音   我要夏天   
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
		try {
			String retXml = jiayouEngineService.process(baseMsgDto,xmlStr );
			out.print(retXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		in.close();
		in = null;
		
		out.close();
		out = null;
	}

	private static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arr = new String[] { TOKEN, timestamp, nonce };
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	// 将字节转换为十六进制字符串
	private static String byteToHexStr(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];

		String s = new String(ob);
		return s;
	}

	// 将字节数组转换为十六进制字符串
	private static String byteToStr(byte[] bytearray) {
		String strDigest = "";
		for (int i = 0; i < bytearray.length; i++) {
			strDigest += byteToHexStr(bytearray[i]);
		}
		return strDigest;
	}

}