package co.dc.server.weixin;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.dc.server.weixin.service.AttentionService;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration   
({"/applicationContent.xml"}) //加载配置文件  
public class BaseJunit4Test {
	@Resource
	private AttentionService attentionService;
	
	@Test
	public void test1(){
		attentionService.saveJiayouAttention();
	}
	
	
}  