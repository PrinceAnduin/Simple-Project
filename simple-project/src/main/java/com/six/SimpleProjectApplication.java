package com.six;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.six.mapper")
public class SimpleProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleProjectApplication.class, args);
	}

}
