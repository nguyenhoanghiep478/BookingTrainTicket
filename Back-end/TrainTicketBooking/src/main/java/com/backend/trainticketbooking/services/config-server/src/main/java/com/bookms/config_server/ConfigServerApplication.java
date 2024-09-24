package com.bookms.config_server;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("PROD_DB_URL", dotenv.get("PROD_DB_URL"));
		System.setProperty("PROD_DB_USERNAME", dotenv.get("PROD_DB_USERNAME"));
		System.setProperty("PROD_DB_PASSWORD", dotenv.get("PROD_DB_PASSWORD"));
		System.setProperty("PROD_ADDITIONAL_PROP", dotenv.get("PROD_ADDITIONAL_PROP"));
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
