package com.web.manage.user.action;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.web.foundation.domain.User;
import com.web.foundation.service.IUserService;
import com.web.utils.email.MailUtil;


@Controller
public class UserLoginAction {
	@Autowired
	private IUserService iUserService;
	
	 /**登录
     * zhou(10.27)
     * @param modeMap
     * @return
     */
    @RequestMapping(value = "/user/loginS.htm", method = RequestMethod.POST)
    @ResponseBody
    public  String login( HttpSession session,@RequestParam(value = "userName") String username,
                        @RequestParam(value = "password") String password, ModelMap modeMap, HttpServletRequest request, HttpServletResponse response, String isAutoLogin) {
        //System.out.println("=>userName:" + username + ",password:" + password + ",pwd:" + MD5Util.string2MD5(password));
    	Map params = new HashMap();
		params.put("name", username);
		//params.put("password", password);
		List<User>  rUser = this.iUserService.query("select obj from User obj where obj.userName=:name", params, -1, -1);
        JSONObject ret = new JSONObject();
        ret.put("status", "error");
        if (rUser.size() == 0) {
            ret.put("msg", 120);
            return ret.toString();
        } else if (password.equals(rUser.get(0).getPassword())) {
            ret.put("status", "success");
            ret.put("data", rUser);
            modeMap.addAttribute("currUser", rUser);      
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(date);
            Timestamp ts = Timestamp.valueOf(time);
            System.out.println("=>>>>loginTimer:" + ts);
            boolean is_first_login = false;
            /*if (rUser.get(0).getCreateTime() != null && !"".equals(rUser.get(0).getLoginTime())) {
                SimpleDateFormat sdf_compare = new SimpleDateFormat("yyyy-MM-dd");
                String time1 = sdf_compare.format(rUser.getLoginTime());

                String time2 = sdf_compare.format(date);

                if (!time1.equals(time2)) {
                    is_first_login = true;
                }
            }*/
            User users = rUser.get(0);
            session.setAttribute("user", users);
            ret.put("url", "/user/menu.htm?item=index_SiteSet");
        } else {
            ret.put("msg", 119);
        }
        return ret.toString();
    }
	
    
    /**
     * 用户注册
     *(zhou 10.30)
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/registerJson.htm", method = RequestMethod.POST)
    @ResponseBody
    public Object registerJson(HttpServletRequest request, HttpSession session,String username, String password,
    		 String password_now,String email,String name, String code){
        System.out.println("=================>user username:" +username + ",pwd:" + password + ",email:" + email);
        JSONObject resultJson = new JSONObject();
        if (!"".equals(username) && username != null && !"null".equals(username)) {
        	Map params = new HashMap();
    		params.put("name", username);
    		//params.put("password", password);
    		List<User>  rUser = this.iUserService.query("select obj from User obj where obj.userName=:name", params, -1, -1);
            if (rUser.size() >0) {
                resultJson.put("msg", 101);//账号已存在
                return resultJson.toString();
            }
        } else {
        	  resultJson.put("msg", 102);
        	  return resultJson.toString(); //账号不能为空
        }
        if (!"".equals(email) && email != null && !"null".equals(email)) {
        	Map paramsEmaill = new HashMap();
        	paramsEmaill.put("email", email);
    		//params.put("password", password);
    		List<User>  rUser = this.iUserService.query("select obj from User obj where obj.email=:email", paramsEmaill, -1, -1);
            if (rUser.size()>0) {
            	  resultJson.put("msg", 103); //邮箱已存在
            	  return resultJson.toString();
            }
        }
        if (!"".equals(email) && email != null) {
            Object phoneCode = request.getSession(false).getAttribute(email);
            if (phoneCode == null && "".equals(phoneCode)) {
              /*  String obj = (String) phoneCode;
                if (!obj.equals(email)) {   */ 
                    resultJson.put("msg", 104); //获取的验证码不存在
                    return resultJson.toString();
               
            } else {
            	if(code.equals(phoneCode)){
           		 resultJson.put("msg", 105);//验证成功
           	}else{
           		 resultJson.put("msg", 98);//验证码不正确
           		 return resultJson.toString();
           	}
            }
        } else {
        	 resultJson.put("msg", 106);
        	  return resultJson.toString(); //验证码不能为空
        }
     /*   if (!"".equals(name) && name != null) {
        	Map paramsName = new HashMap();
        	paramsName.put("name", name);
    		//params.put("password", password);
    		List<User>  rUser = this.iUserService.query("select obj from User obj where obj.name=:name", paramsName, -1, -1);
            if (rUser.size()>0) {
            	 resultJson.put("msg", 107); //昵称已注册
                return resultJson.toString();
            }
        } else {
        	 resultJson.put("msg", 108);
            return resultJson.toString();//昵称不能为空
        }*/

        if (password == null && !"".equals(password)) {
        	 resultJson.put("msg", 109);
            return resultJson.toString(); //密码不能为空
        }
        if (!password.equals(password_now) ){
        	 resultJson.put("msg", 99);
             return resultJson.toString(); //密码不匹配
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        Timestamp ts = Timestamp.valueOf(time);
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setCreateTime(ts);
        user.setStatus(1);
        this.iUserService.save(user);
        session.setMaxInactiveInterval(1);//以秒为单位
        resultJson.put("url", "/user/site_list.htm");
        resultJson.put("status", "success");
        return resultJson.toString();
    }
    /**
     * 注册邮箱验证码
     * (zhou 10.30)
     * @param username
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/getEmail.htm", method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public String getPhoneCode(@RequestParam(value = "email") String email, HttpSession session) throws Exception {
        System.out.println("------you telephone num :" + email);
        JSONObject retJson = new JSONObject();
        if(email!=null && !"".equals(email)){
        	
        	Map params = new HashMap();
    		params.put("email", email);
    		//params.put("password", password);
    		List<User>  rUser = this.iUserService.query("select obj from User obj where obj.email=:email", params, -1, -1);
            if (rUser.size() >0) {
            	retJson.put("msg", 101);//账号已存在
            }else{  	
            	MailUtil.sendEmail(email, "register",session);
        		retJson.put("status", "success");//成功
        	}
        }else{      
        	retJson.put("status", "error");//用户名不能为空
        }    
        return retJson.toString();
    }
    
    
    /**
     * 修改密码
     *（zhou 10.30）
     * @param code
     * @param newPwd
     * @param session
     * @return added by lyh 2016/5/11
     */
    @RequestMapping(value = "/user/updatePassword.htm", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public String updatePassword(@RequestParam(value = "code") String code, @RequestParam(value = "newPwd") String newPwd,@RequestParam(value = "newPwdTow") String newPwdTow,
    		HttpSession session,@RequestParam(value = "email") String email,HttpServletRequest request) {
        System.out.println("新密码" + newPwd);
        JSONObject resultJson = new JSONObject();
       /* Map params = new HashMap();
		params.put("name", username);
		//params.put("password", password);
		List<User>  rUser = this.iUserService.query("select obj from User obj where obj.userName=:name", params, -1, -1);
        if (rUser.size() == 0) {
        	params.put("msg", 101);//账号不存在
        	  return resultJson.toString();
        }*/
        if (!"".equals(email) && email != null && !"null".equals(email)) {
        	Map paramsEmaill = new HashMap();
        	paramsEmaill.put("email", email);
    		//params.put("password", password);
    		List<User>  rUserM = this.iUserService.query("select obj from User obj where obj.email=:email", paramsEmaill, -1, -1);
            if (rUserM.size() == 0) {
            	  resultJson.put("msg", 96); //邮箱不存在
            	  return resultJson.toString();
            }
        }
        Map paramsEmaill = new HashMap();
    	paramsEmaill.put("email", email);
		//params.put("password", password);
		List<User>  rUserM = this.iUserService.query("select obj from User obj where obj.email=:email", paramsEmaill, -1, -1);
        if (!"".equals(email) && email != null) {
            Object phoneCode = request.getSession(false).getAttribute(email);
            if (phoneCode == null && "".equals(phoneCode)) {
              /*  String obj = (String) phoneCode;
                if (!obj.equals(email)) {   */ 
                    resultJson.put("msg", 104); //获取的验证码不存在
                    return resultJson.toString();
               
            } else {
            	if(code.equals(phoneCode)){
           		 resultJson.put("msg", 105);//验证成功
           	}else{
           		 resultJson.put("msg", 98);//验证码不正确
           		 return resultJson.toString();
           	}
            }
        }  else {
        	 resultJson.put("msg", 106);
        	  return resultJson.toString(); //邮箱不能为空
        }
        if (!newPwd.equals(newPwdTow) ){
       	 resultJson.put("msg", 99);
            return resultJson.toString(); //密码不匹配
       }
        rUserM.get(0).setPassword(newPwd);
        this.iUserService.update(rUserM.get(0));
        resultJson.put("url", "/user/menu.htm?item=user_login");
        resultJson.put("status", "success");
        return resultJson.toString();
    }
    
    
    /**
     * 修改密码箱验证码
     * (zhou 10.30)
     * @param username
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/updateEmail.htm", method = RequestMethod.POST, produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public String UpdatePhoneCode(@RequestParam(value = "email") String email, HttpSession session) throws Exception {
        JSONObject retJson = new JSONObject();
        if(email.equals("") && email == null ){
        	retJson.put("status", "error"); //邮箱为空
        	return retJson.toString();
        }
        	Map params = new HashMap();
    		params.put("email", email);
    		//params.put("password", password);
    		List<User>  rUser = this.iUserService.query("select obj from User obj where obj.email=:email", params, -1, -1);
            if (rUser.size()  == 0) {
            	retJson.put("msg", 101);//账号不存在
            }else{  	
            	MailUtil.sendEmail(email, "Update",session);
        		retJson.put("status", "success");//成功
        	}
       
        return retJson.toString();
    }

 
    /**
     * 修改个人信息
     *（zhou 10.30）
     * @param code
     * @param newPwd
     * @param session
     * @return added by lyh 2016/5/11
     */
    @RequestMapping(value = "/user/update_user.htm", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public String update_user(String email,HttpServletRequest request,String id,String password) {
        JSONObject resultJson = new JSONObject();   
        Map params = new HashMap();
       	params.put("id", Integer.parseInt(id));
   		//params.put("password", password);
   		List<User>  rUserM = this.iUserService.query("select obj from User obj where obj.id=:id", params, -1, -1);
        
        if(rUserM.get(0).getEmail().equals(email)){
        	
        }else{
        if (!"".equals(email) && email != null && !"null".equals(email)) {
        	Map paramsEmaill = new HashMap();
        	paramsEmaill.put("email", email);
    		//params.put("password", password);
    		List<User>  rUserM1 = this.iUserService.query("select obj from User obj where obj.email=:email", paramsEmaill, -1, -1);
            if (rUserM1.size() >0) {
            	  resultJson.put("msg", 96); //邮箱存在
            	  return resultJson.toString();
            }
        }
    }
        rUserM.get(0).setPassword(password);
        this.iUserService.update(rUserM.get(0));
        resultJson.put("status", "success");
        return resultJson.toString();
    }
	
}