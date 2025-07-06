package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeHttpRequests(auth ->
				auth.requestMatchers("/", "/eureka/**", "/css/**", "/js/**").authenticated()
				.anyRequest().permitAll()
			)
			.httpBasic()
			.and()
			.csrf(csrf -> csrf.ignoringRequestMatchers("/eureka/**"));
		
		return httpSecurity.build();
	}
	
}
