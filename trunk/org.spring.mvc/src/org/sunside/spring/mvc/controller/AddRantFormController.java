package org.sunside.spring.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

/**
 * 命令控制器的实现
 * Spring3.0建议使用注解控制器 
 */
public class AddRantFormController extends AbstractCommandController {

	@Override
	protected ModelAndView handle(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, BindException arg3)
			throws Exception {
		return null;
	}
	
}
