package com.web.foundation.service.impl;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.core.constant.Globals;
import com.web.core.dao.IGenericDAO;
import com.web.core.tools.CommUtil;
import com.web.foundation.dao.SysConfigDAO;
import com.web.foundation.domain.SysConfig;
import com.web.foundation.service.ISysConfigService;


@Service
@Transactional
public class SysConfigServiceImpl implements ISysConfigService {
	
	@Resource(name = "sysConfigDAO")
	private IGenericDAO<SysConfig> sysConfigDAO;

	@Transactional(readOnly = false)
	public boolean delete(SysConfig shopConfig) {
		// TODO Auto-generated method stub
		return false;
	}

	@Transactional(readOnly = true)
	public SysConfig getSysConfig() {
		// TODO Auto-generated method stub
		List<SysConfig> configs = this.sysConfigDAO.query(
				"select obj from SysConfig obj", null, -1, -1);
		if (configs != null && configs.size() > 0) {
			SysConfig sc = configs.get(0);
			if (sc.getUploadFilePath() == null) {
				sc.setUploadFilePath(Globals.UPLOAD_FILE_PATH);
			}
			if (sc.getSysLanguage() == null) {
				sc.setSysLanguage(Globals.DEFAULT_SYSTEM_LANGUAGE);
			}
			if (sc.getWebsiteName() == null || sc.getWebsiteName().equals("")) {
				sc.setWebsiteName(Globals.DEFAULT_WBESITE_NAME);
			}
			if (sc.getCloseReason() == null || sc.getCloseReason().equals("")) {
				sc.setCloseReason(Globals.DEFAULT_CLOSE_REASON);
			}
			if (sc.getTitle() == null || sc.getTitle().equals("")) {
				sc.setTitle(Globals.DEFAULT_SYSTEM_TITLE);
			}
			if (sc.getImageSaveType() == null
					|| sc.getImageSaveType().equals("")) {
				sc.setImageSaveType(Globals.DEFAULT_IMAGESAVETYPE);
			}
			if (sc.getImageFilesize() == 0) {
				sc.setImageFilesize(Globals.DEFAULT_IMAGE_SIZE);
			}
			if (sc.getSmallWidth() == 0) {
				sc.setSmallWidth(Globals.DEFAULT_IMAGE_SMALL_WIDTH);
			}
			if (sc.getSmallHeight() == 0) {
				sc.setSmallHeight(Globals.DEFAULT_IMAGE_SMALL_HEIGH);
			}
			if (sc.getMiddleWidth() == 0) {
				sc.setMiddleWidth(Globals.DEFAULT_IMAGE_MIDDLE_WIDTH);
			}
			if (sc.getMiddleHeight() == 0) {
				sc.setMiddleHeight(Globals.DEFAULT_IMAGE_MIDDLE_HEIGH);
			}
			if (sc.getBigHeight() == 0) {
				sc.setBigHeight(Globals.DEFAULT_IMAGE_BIG_HEIGH);
			}
			if (sc.getBigWidth() == 0) {
				sc.setBigWidth(Globals.DEFAULT_IMAGE_BIG_WIDTH);
			}
			if (sc.getImageSuffix() == null || sc.getImageSuffix().equals("")) {
				sc.setImageSuffix(Globals.DEFAULT_IMAGE_SUFFIX);
			}
			
			if (sc.getSecurityCodeType() == null
					|| sc.getSecurityCodeType().equals("")) {
				sc.setSecurityCodeType(Globals.SECURITY_CODE_TYPE);
			}
			if (sc.getWebsiteCss() == null || sc.getWebsiteCss().equals("")) {
				sc.setWebsiteCss(Globals.DEFAULT_THEME);
			}
			if (sc.getPayoff_date() == null) {
				Calendar cale = Calendar.getInstance();
				cale.set(Calendar.DAY_OF_MONTH,
						cale.getActualMaximum(Calendar.DAY_OF_MONTH));
				sc.setPayoff_date(cale.getTime());
			}
			if (sc.getSmsURL() == null || sc.getSmsURL().equals("")) {
				sc.setSmsURL(Globals.DEFAULT_SMS_URL);
			}
			if (sc.getAuto_order_notice() == 0) {
				sc.setAuto_order_notice(3);
			}
			if (sc.getAuto_order_evaluate() == 0) {
				sc.setAuto_order_evaluate(7);
			}
			if (sc.getAuto_order_return() == 0) {
				sc.setAuto_order_return(7);
			}
			if (sc.getAuto_order_confirm() == 0) {
				sc.setAuto_order_confirm(7);
			}
			if (sc.getGrouplife_order_return() == 0) {
				sc.setGrouplife_order_return(7);
			}
			return sc;
		} else {
			SysConfig sc = new SysConfig();
			sc.setUploadFilePath(Globals.UPLOAD_FILE_PATH);
			sc.setWebsiteName(Globals.DEFAULT_WBESITE_NAME);
			sc.setSysLanguage(Globals.DEFAULT_SYSTEM_LANGUAGE);
			sc.setTitle(Globals.DEFAULT_SYSTEM_TITLE);
			sc.setSecurityCodeType(Globals.SECURITY_CODE_TYPE);
			sc.setEmailEnable(Globals.EAMIL_ENABLE);
			sc.setCloseReason(Globals.DEFAULT_CLOSE_REASON);
			sc.setImageSaveType(Globals.DEFAULT_IMAGESAVETYPE);
			sc.setImageFilesize(Globals.DEFAULT_IMAGE_SIZE);
			sc.setSmallWidth(Globals.DEFAULT_IMAGE_SMALL_WIDTH);
			sc.setSmallHeight(Globals.DEFAULT_IMAGE_SMALL_HEIGH);
			sc.setMiddleHeight(Globals.DEFAULT_IMAGE_MIDDLE_HEIGH);
			sc.setMiddleWidth(Globals.DEFAULT_IMAGE_MIDDLE_WIDTH);
			sc.setBigHeight(Globals.DEFAULT_IMAGE_BIG_HEIGH);
			sc.setBigWidth(Globals.DEFAULT_IMAGE_BIG_WIDTH);
			sc.setImageSuffix(Globals.DEFAULT_IMAGE_SUFFIX);
			sc.setComplaint_time(Globals.DEFAULT_COMPLAINT_TIME);
			sc.setWebsiteCss(Globals.DEFAULT_THEME);
			sc.setSmsURL(Globals.DEFAULT_SMS_URL);
			Calendar cale = Calendar.getInstance();
			cale.set(Calendar.DAY_OF_MONTH,
					cale.getActualMaximum(Calendar.DAY_OF_MONTH));
			sc.setPayoff_date(cale.getTime());
			sc.setAuto_order_notice(3);
			sc.setAuto_order_evaluate(7);
			sc.setAuto_order_return(7);
			sc.setAuto_order_confirm(7);
			sc.setGrouplife_order_return(7);
			return sc;
		}
	}

	@Transactional(readOnly = false)
	public boolean save(SysConfig shopConfig) {
		// TODO Auto-generated method stub
		try {
			this.sysConfigDAO.save(shopConfig);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = false)
	public boolean update(SysConfig shopConfig) {
		// TODO Auto-generated method stub
		try {
			this.sysConfigDAO.update(shopConfig);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	

	

	
	
	
}
