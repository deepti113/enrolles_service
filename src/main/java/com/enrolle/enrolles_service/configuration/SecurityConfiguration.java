/*
 * package com.enrolle.enrolles_service.configuration;
 * 
 * import org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.method.configuration.
 * EnableGlobalMethodSecurity; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import
 * org.springframework.security.config.annotation.web.builders.WebSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter;
 * 
 * @Configuration
 * 
 * @EnableWebSecurity
 * 
 * @EnableGlobalMethodSecurity(prePostEnabled = true) public class
 * SecurityConfiguration extends WebSecurityConfigurerAdapter {
 * 
 * @Override public void configure(WebSecurity web) {
 * web.ignoring().antMatchers(); }
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception { //
 * http.csrf().disable(); // http.authorizeRequests().anyRequest().permitAll();
 * http.antMatcher("/**").authorizeRequests().antMatchers("/",
 * "/login**").permitAll().anyRequest().authenticated() .and().oauth2Login(); }
 * }
 */