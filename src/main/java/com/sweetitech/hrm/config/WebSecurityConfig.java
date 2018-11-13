package com.sweetitech.hrm.config;

import com.sweetitech.hrm.common.util.PasswordUtil;
import com.sweetitech.hrm.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomUserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        passwordEncoder = PasswordUtil.getBCryptPasswordEncoder();
    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .authenticationProvider(authenticationProvider())
                .jdbcAuthentication();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource());
//    }
//
//    @Value("${spring.datasource.driverClassName}")
//    private String springDatasourceDriverClassName;
//    @Value("${spring.datasource.url}")
//    private String springDatasourceURL;
//    @Value("${spring.datasource.username}")
//    private String springDatasourceUsername;
//    @Value("${spring.datasource.password}")
//    private String springDatasourcePassword;
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource =  new DriverManagerDataSource();
//        dataSource.setDriverClassName(springDatasourceDriverClassName);
//        dataSource.setUrl(springDatasourceURL);
//        dataSource.setUsername(springDatasourceUsername);
//        dataSource.setPassword(springDatasourcePassword);
//        return dataSource;
//    }
//
//
//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices() {
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(tokenStore());
//        defaultTokenServices.setSupportRefreshToken(true);
//        return defaultTokenServices;
//    }

}