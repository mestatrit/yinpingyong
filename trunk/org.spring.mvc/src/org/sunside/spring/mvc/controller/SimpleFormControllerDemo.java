package org.sunside.spring.mvc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.sunside.spring.mvc.controller.domain.User;

/**
 * 处理表单的提交 
 */
public class SimpleFormControllerDemo extends SimpleFormController {

	public SimpleFormControllerDemo() {
		/**
		 * 绑定请求参数反射对象
		 */
		setCommandClass(User.class);
		setCommandName("user");
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		
		User user = (User)super.formBackingObject(request);
		if (user.getUserName() == null) {
			throw new RuntimeException("请输入用户名.");
		}
		
		if (user.getPassword() == null) {
			throw new RuntimeException("请输入密码");
		}
		
		/**
		 * 构造返回对象
		 */
		return new ModelAndView("home", "userInfo", user);
	}
}
