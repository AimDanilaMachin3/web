package com.example.web.conf;

import com.example.web.services.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final private UserDetailsService userDetailsService;
    final private BCryptPasswordEncoder passwordEncoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/static/styles/**").permitAll()
                .antMatchers("/static/images/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/registration/**").permitAll()
                .antMatchers("/catalog/**").permitAll()
                .antMatchers("/profile/**").authenticated()
                .antMatchers("/car/**").permitAll()
                .antMatchers("/error/**").permitAll()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/catalog").failureUrl("/login-error")
                .and().logout().logoutUrl("/exit").logoutSuccessUrl("/login")
                .and().exceptionHandling().accessDeniedPage("/error");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

}
