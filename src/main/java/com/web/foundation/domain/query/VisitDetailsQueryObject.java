package com.web.foundation.domain.query;
import org.springframework.web.servlet.ModelAndView;

import com.web.core.query.QueryObject;

public class VisitDetailsQueryObject extends QueryObject {
	
	public VisitDetailsQueryObject(String currentPage, ModelAndView mv,
			String orderBy, String orderType) {
		super(currentPage, mv, orderBy, orderType);
		// TODO Auto-generated constructor stub
	}
	public VisitDetailsQueryObject(String construct, String currentPage,
			ModelAndView mv, String orderBy, String orderType) {
		super(construct, currentPage, mv, orderBy, orderType);
		// TODO Auto-generated constructor stub
	}

	public VisitDetailsQueryObject() {
		super();
		// TODO Auto-generated constructor stub
	}
}
