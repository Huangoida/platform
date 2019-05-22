package com.glacierluo.platform.config;


import com.glacierluo.platform.handler.ImpAuthenticationFailureHandler;
import com.glacierluo.platform.handler.ImpAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String resource_id = "user";

    @Autowired
    private ImpAuthenticationSuccessHandler impAuthenticationSuccessHandler;
    @Autowired
    private ImpAuthenticationFailureHandler impAuthenticationFailureHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resourceServerSecurityConfigurer) {
        resourceServerSecurityConfigurer.resourceId(resource_id).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/user/**").authenticated();

        http.formLogin()
                //用户名登录地址
                .loginProcessingUrl("/form/token")
                .permitAll();


    }
}
