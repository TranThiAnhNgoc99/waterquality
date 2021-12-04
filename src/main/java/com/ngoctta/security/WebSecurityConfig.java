package com.ngoctta.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Configuration
	@Order(1)
	public static class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {
		@Bean
		public JwtFilter authJwtTokenFilter() {
			return new JwtFilter();
		}

		@Autowired
		private JwtEntryPoint jwtEntryPoint;

		@Bean
		@Override
		public AuthenticationManager authenticationManager() throws Exception {
			// TODO Auto-generated method stub
			return super.authenticationManager();
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/fonts/**");
			web.ignoring().antMatchers("/v3/api-docs/**");
			web.ignoring().antMatchers("/swagger.json");
			web.ignoring().antMatchers("/swagger-ui.html");
			web.ignoring().antMatchers("/swagger-ui/**");
			web.ignoring().antMatchers("/swagger-resources/**");
			web.ignoring().antMatchers("/webjars/**");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().authorizeRequests()
					.antMatchers("/api/login/**", "/login/**", "/swagger-ui.html/**", "/swagger-ui/****",
							"/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/")
					.permitAll().antMatchers("/api/admin/**").access("hasRole('ADMIN')")
					// .antMatchers("/admin/**").hasRole("ADMIN")
					.antMatchers("/api/admin/**").authenticated().and().exceptionHandling()
					.accessDeniedHandler(new SimpleAccessDeniedHandler()).authenticationEntryPoint(jwtEntryPoint).and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

			http.addFilterBefore(authJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		}
	}

	@Configuration
	@Order(2)
	public static class LoginFormSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		private PasswordEncoder passwordEncoder;

		@Autowired
		private UserDetailsService userDetailsService;

//		@Autowired
//		CustomSuccessHandler customSuccessHandler;

		@Override
		protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/fonts/**", "/img/**");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().authorizeRequests().antMatchers("/api/login/**", "/login/**",  "/").permitAll()
					.antMatchers("/admin/**").hasRole("ADMIN").and().formLogin().loginPage("/login")
					.loginProcessingUrl("/checklogin")
					// .successHandler(customSuccessHandler)
					.permitAll();
			// .failureUrl("/login?error=Username or Password is not exist");
			http.sessionManagement().maximumSessions(1).expiredUrl("/customlogin?expired=true");
		}
	}

}
