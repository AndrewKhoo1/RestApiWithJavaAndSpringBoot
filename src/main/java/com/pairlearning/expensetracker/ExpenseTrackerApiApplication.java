package com.pairlearning.expensetracker;

import com.pairlearning.expensetracker.filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//create a filter registration bean (part of spring framework) to register our custom filters
@SpringBootApplication
public class ExpenseTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApiApplication.class, args);
	}

	//create an instance of FilterRegistrationBean below then create an instance of the custom AuthFilter
	//this filter will be applied to protected resources e.g. endpoints to manipulate categories and transactions using addUrls method of the filter bean
	//e.g. any urls starting with /api/categories
	//however login and register endpoints don't have to be protected

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		source.registerCorsConfiguration("/**", config);
		registrationBean.setFilter(new CorsFilter(source));
		registrationBean.setOrder(0);
		return registrationBean;
	}


	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/categories/*");
		return registrationBean;
	}
}
