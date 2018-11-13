package com.sweetitech.hrm;

import com.sweetitech.hrm.config.StorageProperties;
import com.sweetitech.hrm.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableScheduling
public class TemplateApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TemplateApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TemplateApplication.class);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+06:00"));
		System.out.println("====== Timezone set to Dhaka ======");
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//   storageService.deleteAll();
			storageService.init();
		};
	}
}
