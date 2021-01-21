package com.liufuhao.crowd.mvc.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.liufuhao.crowd.constant.CrowdConstant;
import com.liufuhao.crowd.exception.LoginAcctAlreadyInUseException;
import com.liufuhao.crowd.exception.LoginAcctAlreadyInUseforUpdateException;
import com.liufuhao.crowd.exception.LoginFailedException;
import com.liufuhao.crowd.util.CrowdUtil;
import com.liufuhao.crowd.util.ResultEntity;

@ControllerAdvice
public class CrowdExceptionResolver {
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView resolveException(Exception exception, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		String viewName = "admin-login";
		return commonResolveException(exception, request, response, viewName);
	}

	@ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
	public ModelAndView resolveLoginAcctAlreadyInUseException(LoginAcctAlreadyInUseException exception, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		String viewName = "admin-add";
		return commonResolveException(exception, request, response, viewName);
	}
	@ExceptionHandler(value = LoginFailedException.class)
	public ModelAndView resolveLoginFailedException(LoginFailedException exception, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		String viewName = "admin-login";
		return commonResolveException(exception, request, response, viewName);
	}
	@ExceptionHandler(value = LoginAcctAlreadyInUseforUpdateException.class)
	public ModelAndView resolveLoginAcctAlreadyInUseforUpdateException(LoginAcctAlreadyInUseforUpdateException exception, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		String viewName = "system-error";
		return commonResolveException(exception, request, response, viewName);
	}
	
	/**
	* 核心异常处理方法
	* @param exception SpringMVC 捕获到的异常对象
	* @param request 为了判断当前请求是“普通请求”还是“Ajax 请求” 需要传入原生 request 对象
	* @param response 为了能够将 JSON 字符串作为当前请求的响应数据返回给浏览器
	* @param viewName 指定要前往的视图名称
	* @return ModelAndView
	* @throws IOException
	*/
	private ModelAndView commonResolveException(Exception exception, HttpServletRequest request,
			HttpServletResponse response, String viewName) throws IOException {
		// 1.判断当前请求是“普通请求”还是“Ajax 请求”
		boolean judgeResult = CrowdUtil.judgeRequestType(request);
		// 2.如果是 Ajax 请求
		if (judgeResult) {
			// 3.从当前异常对象中获取异常信息
			String message = exception.getMessage();
			// 4.创建 ResultEntity
			ResultEntity<Object> resultEntity = ResultEntity.failed(message);
			// 5.创建 Gson 对象
			Gson gson = new Gson();
			// 6.将 resultEntity 转化为 JSON 字符串
			String json = gson.toJson(resultEntity);
			// 7.把当前 JSON 字符串作为当前请求的响应体数据返回给
			// ①获取 Writer 对象
			PrintWriter writer = response.getWriter();
			// ②写入数据
			writer.write(json);
			// 8.返回 null，不给 SpringMVC 提供 ModelAndView 对象
			// 这样 SpringMVC 就知道不需要框架解析视图来提供响应，
			return null;
		}
		// 9.创建 ModelAndView 对象
		ModelAndView modelAndView = new ModelAndView();
		// 10.将 Exception 对象存入模型
		modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
		// 11.设置目标视图名称
		modelAndView.setViewName(viewName);
		// 12.返回 ModelAndView 对象
		return modelAndView;
	}
}
