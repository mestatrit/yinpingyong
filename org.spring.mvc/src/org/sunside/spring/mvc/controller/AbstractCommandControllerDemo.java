package org.sunside.spring.mvc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;
import org.sunside.spring.mvc.controller.domain.User;

/**
 * 命令控制器的实现
 * Spring3.0建议使用注解控制器 
 */
public class AbstractCommandControllerDemo extends AbstractCommandController {

	public AbstractCommandControllerDemo() {
		
		/**
		 * 用于请求参数反射成对应的OO
		 */
		setCommandClass(User.class);
		setCommandName("user");
	}
	
	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors)
			throws Exception {
		
		/**
		 * 自定获取请求对象信息
		 */
		User user = (User)command;
		if (user.getUserName() == null) {
			throw new RuntimeException("请输入用户名.");
		}
		
		if (user.getPassword() == null) {
			throw new RuntimeException("请输入密码");
		}
		
		//TODO 业务处理
		
		/**
		 * 构造返回对象
		 */
		Map model = errors.getModel();
		model.put("user", user);
		return new ModelAndView("home", model);
	}
	
}
