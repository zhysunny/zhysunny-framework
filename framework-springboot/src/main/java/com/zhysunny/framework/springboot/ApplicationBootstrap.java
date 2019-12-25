package com.zhysunny.framework.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication(scanBasePackages = { "com.zhysunny.framework.springboot.controller", "com.zhysunny.framework.springboot.service.impl" })
@ServletComponentScan
@MapperScan(basePackages = { "com.zhysunny.framework.springboot.mapper" })
public class ApplicationBootstrap {

	public static void main(String[] args) throws IOException {
		// 修改application.properties默认路径
		Properties properties = new Properties();
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/application.properties");
		properties.load(is);
		is.close();
		// 启动springboot服务
		SpringApplication springApplication = new SpringApplication(ApplicationBootstrap.class);
		springApplication.setDefaultProperties(properties);
		springApplication.run(args);
	}

}
