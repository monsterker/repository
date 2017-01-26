package config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import model.CustomerService;
import model.ProductService;
import model.dao.CustomerDAOJdbc;
import model.dao.ProductDAOJdbc;

@Configuration
public class SpringJavaConfig {
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = null;
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/xxx");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return dataSource;
	}
	@Bean
	public CustomerService customerService() {
		return new CustomerService(new CustomerDAOJdbc(dataSource()));
	}
	@Bean
	public ProductService productService() {
		return new ProductService(new ProductDAOJdbc(dataSource()));
	}
}
