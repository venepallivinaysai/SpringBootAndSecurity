package com.springBlog.SpringBlog;

import com.springBlog.SpringBlog.dao.RoleRepository;
import com.springBlog.SpringBlog.entities.Role;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App",
				description = "Details of all the REST APIs",
				contact = @Contact(
						name = "Vinay Venepalli",
						email = "vinayvenepalli@gmail.com"
				)
		)
)
public class SpringBlogApplication{

	public static void main(String[] args) {
		SpringApplication.run(SpringBlogApplication.class, args);
	}
	
	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}


}
