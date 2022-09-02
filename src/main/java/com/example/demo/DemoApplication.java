package com.example.demo;

import com.example.demo.config.AppConfig;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 开启事务
@EnableTransactionManagement
// 启动时初始化配置 AppConfig 的属性
@EnableConfigurationProperties({AppConfig.class})
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
//		ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);

		SpringApplication app = new SpringApplication(DemoApplication.class);
		// 关闭 banner
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}
