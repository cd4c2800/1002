package com.web.utils.email;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

/**
*
*/ 
 
/**  
 * 邮件发送工具实现类  
 *   
 * @author shadow  
 * @create 2013/07/12  
 */  
public class MailUtil {  
	private static Properties   p=null;
	//验证码随机数组
	private static String[] captchaArray=null;
	//验证码的大小个数
	private static Integer captchaNum=0;
	private static Properties prop=null;
	//获得配置文件的路径
	private static String dbPath =MailUtil.class.getClassLoader().getResource("tools.properties").getPath();
	/** 
	 * 读取配置文件 获得基本参数
	 */ 
	static{
		try {
			FileInputStream fis=new FileInputStream(dbPath);
			p=new Properties();
			prop=new Properties();
			p.load(fis);			
			captchaArray= ((String) p.get("captcha.char")).split(",");
			captchaNum=Integer.parseInt((String) p.get("captcha.num"));
			prop.setProperty("mail.host",(String) p.get("mail.host"));
	        prop.setProperty("mail.transport.protocol",(String) p.get("mail.transport.protocol"));
	        prop.put("mail.smtp.auth", (String) p.get("mail.smtp.auth"));  
	        prop.put("mail.smtp.host",(String) p.get("mail.smtp.host"));  
	        //你自己的邮箱  
	        prop.put("mail.user", (String) p.get("mail.user"));   
	        //你开启pop3/smtp时的验证码  
	        prop.put("mail.password",(String) p.get("mail.password"));  
	        prop.put("mail.smtp.port", (String) p.get("mail.smtp.port"));  
	        prop.put("mail.smtp.starttls.enable",(String) p.get("mail.smtp.starttls.enable"));    
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
    /**
     * @param args
     * @throws Exception 
     * @param recipient 邮件的 接收人
     * @param passwordUrl 修改密码的 Url
     * 
     */
    public static void sendEmail(String recipient,String passwordUrl,HttpSession sessionM) throws Exception {       
        /*Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.qq.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true");  
        prop.put("mail.smtp.host", "smtp.qq.com");  
        //你自己的邮箱  
        prop.put("mail.user", "1251524110@qq.com");   
        //你开启pop3/smtp时的验证码  
        prop.put("mail.password", "gfzqwhvmicgojhcc");  
        prop.put("mail.smtp.port", "25");  
        prop.put("mail.smtp.starttls.enable", "true");  */      
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、连上邮件服务器，需要发件人提供邮箱的用户名和密码进行验证
        ts.connect(prop.getProperty("mail.smtp.host"),prop.getProperty("mail.user"),prop.getProperty("mail.password"));
        //4、创建邮件
        Message message = createImageMail(session,recipient,passwordUrl,sessionM);
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }
    
    /**
    * @Method: createImageMail
    * @Description: 生成一封邮件正文带图片的邮件
    * @Anthor:孤傲苍狼
    *
    * @param session
    * @return
    * @throws Exception
    */ 
    public static MimeMessage createImageMail(Session session,String recipient,String passwordUrl,HttpSession sessionM) throws Exception {
    	String captcha=getCaptcha();
    	//创建邮件
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的基本信息
        //发件人
        message.setFrom(new InternetAddress(prop.getProperty("mail.user")));
        //收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        String content=null;
        String subject=null;
        
         if(passwordUrl.equals("register")){
        	 content="你的注册验证码是:"+captcha;
 			subject="验证码";
 			sessionM.setAttribute(recipient, captcha);
         }else{
        	 content="你的修好密码验证码是:"+captcha;
  			subject="验证码";
  			sessionM.setAttribute(recipient, captcha);
         }
       /* if (passwordUrl!=null) {
			content="请点击密码修改： "+passwordUrl;
			subject="修改密码";
		}else {
			content="你的验证码是:"+captcha;
			subject="验证码";
			sessionM.setAttribute(recipient, captcha);
			}*/
        //邮件标题
        message.setSubject(subject);

        // 准备邮件数据
        // 准备邮件正文数据
        MimeBodyPart text = new MimeBodyPart();      
        text.setContent(content, "text/html;charset=UTF-8");
        // 准备图片数据
        //MimeBodyPart image = new MimeBodyPart();
        //DataHandler dh = new DataHandler(new FileDataSource("src\\1.jpg"));
        // image.setDataHandler(dh);
        //image.setContentID("1.jpg");
        // 描述数据关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        //mm.addBodyPart(image);
        mm.setSubType("related");

        message.setContent(mm);
        message.saveChanges();
        //将创建好的邮件写入到E盘以文件的形式进行保存
        // message.writeTo(new FileOutputStream("F:\\ImageMail.eml"));
        //返回创建好的邮件
        return message;
    }
	private static String getCaptcha(){
		String mssage="";
		for (int i = 0; i < captchaNum; i++) {
				mssage+=captchaArray[new Random().nextInt(captchaArray.length)];		
		}		
		return mssage;
	}

}  
