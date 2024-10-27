package org.example.wishlist.config;

import org.example.wishlist.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.example.wishlist.config.AppConstant.*;


@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(LOGIN_PAGE, REGISTER_PAGE).permitAll()
                .antMatchers(INDEX_PAGE).authenticated()
                .and()
                .formLogin().defaultSuccessUrl(ROOT_PAGE)
                .and()
                .logout().logoutSuccessUrl(LOGIN_PAGE);
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}