package com.liufuhao.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWeMvcConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		String urlPath = "/auth/member/to/reg/page";
		
		String viewName = "member-reg";
		
		registry.addViewController(urlPath).setViewName(viewName);
		registry.addViewController("/auth/member/to/login/page").setViewName("member-login");
		registry.addViewController("/auth/member/to/center/page").setViewName("member-center");
		registry.addViewController("/member/my/crowd").setViewName("member-crowd");
	}
	
}
