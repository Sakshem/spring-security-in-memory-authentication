package com.sakshem.springsecurityforspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


// this tells spring security that this is a web security configuration.
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // set my configuration on the auth object
        // making use of method chaining
        auth.inMemoryAuthentication()
                .withUser("foo")
                .password("foobar")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("test123")
                .roles("ADMIN");

        // JDBC authentication
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
         return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // order should be from most restrictive URL to least
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .and().formLogin();
    }
}
