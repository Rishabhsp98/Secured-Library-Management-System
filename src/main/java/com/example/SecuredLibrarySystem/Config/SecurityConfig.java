package com.example.SecuredLibrarySystem.Config;

import com.example.SecuredLibrarySystem.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//    @Autowired
//    securityService securityservice;



    /*here we will create the user which are passed from frontend,
    In place of InMemory we are using userDetails from spring security,
    1. our service is implementing the securedService
    2. our model is implementing the userDetails model
    3. here we are just passing the details to add user via in built way of userDetails
        */
//    @Bean
//    protected void configureUser(AuthenticationManagerBuilder auth) throws Exception {
//           auth.userDetailsService(securityservice);
//    }


    //new way to do userDetails service mapping , or to tell that we are using userDetail service in our app
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    //for authorization
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET,"/student/**").hasAuthority(Constants.STUDENT_SELF_INFO_AUTHORITY) //only for student
                        .requestMatchers(HttpMethod.GET,"/student-by-id/**").hasAuthority(Constants.STUDENT_INFO_AUTHORITY)    //only for admin
                        .requestMatchers(HttpMethod.POST,"/admin/**").hasAuthority(Constants.CREATE_ADMIN_AUTHORITY)
                        .requestMatchers("/**").permitAll()
        ).formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
