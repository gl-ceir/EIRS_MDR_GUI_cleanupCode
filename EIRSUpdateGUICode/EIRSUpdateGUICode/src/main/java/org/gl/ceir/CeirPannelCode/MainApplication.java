package org.gl.ceir.CeirPannelCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
/*
 * @SpringBootApplication (properties =
 * "spring.main.allow-bean-definition-overriding=true")
 */
@EnableCaching
@EnableFeignClients
@EnableAutoConfiguration
@SpringBootConfiguration
/* @EnableWebMvc */
@ComponentScan(basePackages = "org.gl.ceir")

/*
 * @PropertySource({ "file:/u01/ceirapp/GUITemp/application.properties",
 * "file:/u01/ceirapp/GUITemp/messages.properties",
 * "file:/u01/ceirapp/GUITemp/messages_km.properties"})
 */
 	
	
  @PropertySource({ "file:${GUI_CONFIG_PATH}/application.properties", "file:${GUI_CONFIG_PATH}/messages.properties",
   "file:${GUI_CONFIG_PATH}/messages_km.properties" })
 

public class MainApplication extends SpringBootServletInitializer {
	
	private final static Logger log = LoggerFactory.getLogger(MainApplication.class);
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MainApplication.class);
	}

	public static void main(String[] args) {
		//log.info(${GUI_CONFIG_PATH});
		
		//String java_home = System.getenv("GUI_CONFIG_PATH");
		//log.info("java_home---" +java_home);
		
		//System.setProperty("GUI_CONFIG_PATH", "/u01/eirsapp/GUI" );
		
		SpringApplication.run(MainApplication.class, args);

	}
}