package com.web.utils.captcha;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * 生成验证码 响应到页面
 * */
public class CaptchaUtil  {
	public static String out(Integer viewWidth,Integer viewHeight,Integer captchaNum,HttpServletResponse response){
		Captcha captcha = new GifCaptcha(viewWidth,viewHeight,captchaNum);//   gif格式动画验证码
		try {
		 return	captcha.out(response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
		
		
		
	}
}
