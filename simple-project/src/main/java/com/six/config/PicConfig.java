package com.six.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PicConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println("配置生效");
        registry.addResourceHandler("/asserts/**").addResourceLocations("file:D:/git/simple-project/src/main/resources/static/asserts/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
	}
}
