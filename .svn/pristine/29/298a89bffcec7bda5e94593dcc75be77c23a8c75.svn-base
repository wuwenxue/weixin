/*
 *
 * Copyright 2010-2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.dc.server.weixin.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传
 * @author zhangcy
 * 2013-6-24
 */
public class Upload {
	
	/**
	 * 上传图片
	 */
	public String uploadImage(@RequestParam MultipartFile picUrl){
		//获取系统时间
		String now = String.valueOf(System.currentTimeMillis());
		//保存新的图片路径
		String picPath = readValue().getProperty("IMAGEPATH");
		//根据路径创建文件
		File picSaveFile = new File(picPath);
		if(!picSaveFile.exists()){
			picSaveFile.mkdirs();
		}
		//自定义文件的后缀
		String suffix = ".jpg";
		//新的文件名和完整路径
		String fileNamess = picPath+now+suffix;
		File file = new File(fileNamess);
		try {
			picUrl.transferTo(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return file.getPath();
	}
        
        /**
         * 获取配置文件路径,取出图片路径
         * @return  Properties
         */
        public  Properties readValue() {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");    
            Properties p = new Properties();    
            try {    
             p.load(inputStream);
            } catch (IOException e1) {    
             e1.printStackTrace();    
            }    
            //String imagePath = p.getProperty("IMAGEPATH");
            return p;
        }

        
	
}
