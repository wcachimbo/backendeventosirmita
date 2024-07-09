package com.backend.eventos.irmita;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


//@SpringBootApplication(exclude = {ConexionBD.class})
//@ComponentScan(basePackages = { "com.backend.eventos.irmita" })
@EnableJpaRepositories
@SpringBootApplication
@Configuration
@EnableTransactionManagement
public class Application {

	public static void main(String[] args)  {
		SpringApplication.run(Application.class, args);

	}

}
