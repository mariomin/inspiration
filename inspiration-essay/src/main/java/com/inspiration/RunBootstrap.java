package com.inspiration;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ImportResource(locations = { "classpath:essay.xml" })
@RestController
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class RunBootstrap extends SpringBootServletInitializer {

	private static void visitHomepage(String url) {
		try {
			if (SystemUtils.IS_OS_WINDOWS) {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			} else if (SystemUtils.IS_OS_MAC_OSX) {
				Runtime.getRuntime().exec("open " + url);
			}
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(RunBootstrap.class, args);

		String port = context.getEnvironment().getProperty("server.port");
		String homeUrl = "http://localhost";
		if (!port.equals("80")) {
			homeUrl += ":" + port;
		}

		visitHomepage(homeUrl + context.getEnvironment().getProperty("server.context-path"));
	}

}
