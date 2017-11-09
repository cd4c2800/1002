package com.web.manage.user.action;
//package com.web.manage.user.action;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.Serializable;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Controller;
//
//import java.text.ParseException;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.web.core.mv.JModelAndView;
//import com.web.core.query.support.IPageList;
//import com.web.core.tools.CommUtil;
//import com.web.core.tools.Md5Encrypt;
//import com.web.core.tools.WebForm;
//import com.web.core.beans.BeanUtils;
//import com.web.core.beans.BeanWrapper;
//import com.web.foundation.domain.User;
//import com.web.foundation.domain.query.UserQueryObject;
//import com.web.foundation.service.ISysConfigService;
//import com.web.foundation.service.IUserConfigService;
//import com.web.foundation.service.IUserService;
//
//@Controller
//public class UserManageAction {
//	@Autowired
//	private ISysConfigService configService;
//	@Autowired
//	private IUserConfigService  userConfigService;
//	@Autowired
//	private IUserService userService;	
//	/**
//	 * user列表页
//	 * 
//	 * @param currentPage
//	 * @param orderBy
//	 * @param orderType
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/admin/user_list.do")
//	public ModelAndView list(HttpServletRequest request,HttpServletResponse response,String currentPage, String orderBy,
//			String orderType) {
//		ModelAndView mv = new JModelAndView("admin/blue/user_list.html", configService
//				.getSysConfig(),this.userConfigService.getUserConfig(), 0, request,response);
//		String url = this.configService.getSysConfig().getAddress();
//		if (url == null || url.equals("")) {
//			url = CommUtil.getURL(request);
//		}
//		String params = "";
//		UserQueryObject qo = new UserQueryObject(currentPage, mv, orderBy,
//				orderType);
//		// WebForm wf = new WebForm();
//		// wf.toQueryPo(request, qo,user.class,mv);
//		IPageList pList = this.userService.list(qo);
//		CommUtil.saveIPageList2ModelAndView(url + "/admin/user_list.htm","",
//				params, pList, mv);
//		return mv;
//	}
//	/**
//	 * user添加管理
//	 * 
//	 * @param request
//	 * @return
//	 * @throws ParseException
//	 */
//	@RequestMapping("/admin/user_add.htm")
//	public ModelAndView add(HttpServletRequest request,HttpServletResponse response,String currentPage) {
//		ModelAndView mv = new JModelAndView("admin/blue/user_add.html", configService
//				.getSysConfig(),this.userConfigService.getUserConfig(), 0, request,response);
//		mv.addObject("currentPage", currentPage);
//		return mv;
//	}
//	/**
//	 * user编辑管理
//	 * 
//	 * @param id
//	 * @param request
//	 * @return
//	 * @throws ParseException
//	 */
//	@RequestMapping("/admin/user_edit.htm")
//	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response,String id,String currentPage)
//        {
//		ModelAndView mv = new JModelAndView("admin/blue/user_add.html", configService
//				.getSysConfig(),this.userConfigService.getUserConfig(), 0, request,response);
//		if (id != null&&!id.equals("")) {
//			User user = this.userService.getObjById(Long.parseLong(id));
//				mv.addObject("obj", user);
//		    mv.addObject("currentPage", currentPage);
//		    mv.addObject("edit", true);
//		}
//		return mv;
//	}
//     /**
//		 * user保存管理
//		 * 
//		 * @param id
//		 * @return
//		 */
//	@RequestMapping("/admin/user_save.htm")
//	public ModelAndView save(HttpServletRequest request,HttpServletResponse response, String id,String currentPage, String cmd, String list_url, String add_url) {
//		WebForm wf = new WebForm();
//		User user =null;
//		if (id.equals("")) {
//			 user = wf.toPo(request, User.class);
//			user.setAddTime(new Date());
//		}else{
//			User obj=this.userService.getObjById(Long.parseLong(id));
//			user = (User)wf.toPo(request,obj);
//		}
//		
//		if (id.equals("")) {
//			this.userService.save(user);
//		} else
//			this.userService.update(user);
//	   ModelAndView mv = new JModelAndView("admin/blue/success.html",
//					configService.getSysConfig(), this.userConfigService
//							.getUserConfig(), 0, request,response);
//			mv.addObject("list_url", list_url);
//			mv.addObject("op_title", "保存user成功");
//			if (add_url != null) {
//				mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
//			}
//		return mv;
//	}
//	@RequestMapping("/admin/user_del.htm")
//	public String delete(HttpServletRequest request,HttpServletResponse response,String mulitId,String currentPage) {
//		String[] ids = mulitId.split(",");
//		for (String id : ids) {
//			if (!id.equals("")) {
//				User user = this.userService.getObjById(Long.parseLong(id));
//			  this.userService.delete(Long.parseLong(id));
//			}
//		}
//		return "redirect:user_list.htm?currentPage="+currentPage;
//	}
//	@RequestMapping("/admin/user_ajax.htm")
//	public void ajax(HttpServletRequest request, HttpServletResponse response,
//			String id, String fieldName, String value)
//			throws ClassNotFoundException {
//		User obj = this.userService.getObjById(Long.parseLong(id));
//		Field[] fields = User.class.getDeclaredFields();
//		BeanWrapper wrapper = new BeanWrapper(obj);
//		Object val = null;
//		for (Field field : fields) {
//			// System.out.println(field.getName());
//			if (field.getName().equals(fieldName)) {
//				Class clz = Class.forName("java.lang.String");
//				if (field.getType().getName().equals("int")) {
//					clz = Class.forName("java.lang.Integer");
//				}
//				if (field.getType().getName().equals("boolean")) {
//					clz = Class.forName("java.lang.Boolean");
//				}
//				if (!value.equals("")) {
//					val = BeanUtils.convertType(value, clz);
//				} else {
//					val = !CommUtil.null2Boolean(wrapper
//							.getPropertyValue(fieldName));
//				}
//				wrapper.setPropertyValue(fieldName, val);
//			}
//		}
//		this.userService.update(obj);
//		response.setContentType("text/plain");
//		response.setHeader("Cache-Control", "no-cache");
//		response.setCharacterEncoding("UTF-8");
//		PrintWriter writer;
//		try {
//			writer = response.getWriter();
//			writer.print(val.toString());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//}