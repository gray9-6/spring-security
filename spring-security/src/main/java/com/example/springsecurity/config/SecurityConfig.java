package com.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails student = User.builder().username("Ajay")
                .password(getPasswordEncoder().encode("ajay123"))
                .roles("STUDENT")
                .build();

        UserDetails admin = User.builder().username("Admin")
                .password(getPasswordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        // we have created two roles i.e student and admin
        // now these two roles does not require large memory to store, so we can use it in RAM
        // and the technical name for that is InMemory
        // return new InMemoryUserDetailsManager(student,admin);

        return new CustomUserDetailService();
    }


    // this bean will check for that user and password is correct or not (basically check the credential)
    @Bean
    public AuthenticationProvider getAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{

        // csrf - cross site request forgery
         httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/public/**")
                .permitAll()
                 .requestMatchers("/student/**")
                 .hasAnyRole("STUDENT","ADMIN")
                 .requestMatchers("/admin/**")
                 .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();

        return httpSecurity.build();
    }
}

// so whenever we send a request , with that request a csrf token also goes, it is a security mechanism
// that basically identifies that the request is coming from genuine source or not
// but here we are implementing our own security method , so we don't need the csrf, we are disabling

// ** means it can be anything