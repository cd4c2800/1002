package com.web.foundation.domain.query;
import org.springframework.web.servlet.ModelAndView;

import com.web.core.query.QueryObject;

public class AdQueryObject extends QueryObject {
	
	public AdQueryObject(String currentPage, ModelAndView mv,
			String orderBy, String orderType) {
		super(currentPage, mv, orderBy, orderType);
		// TODO Auto-generated constructor stub
	}
	public AdQueryObject(String construct, String currentPage,
			ModelAndView mv, String orderBy, String orderType) {
		super(construct, currentPage, mv, orderBy, orderType);
		// TODO Auto-generated constructor stub
	}

	public AdQueryObject() {
		super();
		// TODO Auto-generated constructor stub
	}
}
