package org.sunside.spring.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * 最简单的控制器：直接继承核心控制器AbstractController
 */
public class HomePageController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		ModelAndView mv = new ModelAndView("home", "test", "test");
		
		int i = 1/0;
		
		return mv;
	}

}
