package com.backend.eventos.irmita;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

//@SpringBootApplication(exclude = {ConexionBD.class})
//@ComponentScan(basePackages = { "com.backend.eventos.irmita" })
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(Application.class, args);

	}

}
