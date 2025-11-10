package com.quantafic.JWTSecurity.Config;

import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.quantafic.JWTSecurity.Config.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtFilter jwtfilter;
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.cors(c->{});
        http.csrf( customizer -> customizer.disable())
            .authorizeHttpRequests(request -> request
                    .requestMatchers("user/login").permitAll()
                    .requestMatchers("admin/**").permitAll()
                    .requestMatchers("bankstaff/**").hasRole("BankStaff")
                    .requestMatchers("creditanalyst/**").hasRole("CreditAnalyst")
                    .requestMatchers("creditmanager/**").hasRole("CreditManager")
                    .requestMatchers("application/**").permitAll()
                    .requestMatchers("bureau/**").hasAnyRole("CreditManager" , "CreditAnalyst")
                    .requestMatchers("loan-officer").hasRole("LoanOfficer")
                    .requestMatchers("branch-manager/**").hasRole("BranchManager")
                    .requestMatchers("customer/**").permitAll()
                    .anyRequest().permitAll())
            .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtfilter , UsernamePasswordAuthenticationFilter.class);
        //       http.formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
