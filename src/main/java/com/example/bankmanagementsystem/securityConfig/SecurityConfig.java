package com.example.bankmanagementsystem.securityConfig;

import com.example.bankmanagementsystem.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/register","/api/v1/auth/login").permitAll()
                .requestMatchers("/api/v1/auth/users","/api/v1/auth/update/","/api/v1/auth/delete/"
                        ,"/api/v1/employee/add/","/api/v1/employee/update/","/api/v1/employee/employees","/api/v1/employee/delete/"
                        ,"/api/v1/customer/customers", "/api/v1/customer/delete/"
                        ,"/api/v1/account/accounts","/api/v1/account/update/","/api/account/delete/").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/update"
                        ,"/api/v1/account/add","/api/v1/account/activate/","/api/v1/account/get/","/api/v1/account/get-all"
                        ,"/api/v1/account/deposit/{account_id}/{amount}",
                        "/api/v1/account/withdraw/{account_id}/{amount}"
                        ,"/api/v1/account/transfer/{from_account_id}/{to_account_id}/{amount}"
                        ,"/api/v1/account/transaction/{account_id}/{type}/{amount}"
                        ,"/api/v1/account/block/{account_id}").hasAuthority("CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
