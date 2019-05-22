package com.glacierluo.platform.config;

import com.glacierluo.platform.handler.ImpAuthenticationFailureHandler;
import com.glacierluo.platform.handler.ImpAuthenticationSuccessHandler;
import com.glacierluo.platform.service.OauthAuthenticationProvider;
import com.glacierluo.platform.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(2)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //自定义UserDetailsService注入
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private ImpAuthenticationSuccessHandler impAuthenticationSuccessHandler;
    @Autowired
    private ImpAuthenticationFailureHandler impAuthenticationFailureHandler;

    @Autowired
    private OauthAuthenticationProvider oauthAuthenticationProvider;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    //匹配用户时密码规则
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.requestMatchers().antMatchers("/oauth/**","/auth/authorize","logout/**","/auth/login")
                .and()
                //http.authorizeRequests() 方法具有多个matcher子节点，每一个matcher都展示了其自身对应的路径与权限的匹配。
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .failureUrl("/auth/login?error=true")
                .loginProcessingUrl("/auth/authorize")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/index")
                .permitAll()
                //.failureHandler(impAuthenticationFailureHandler)

                .and()
                .logout().logoutUrl("/signout").logoutSuccessUrl("/signin");
    }



    //配置全局方法拦截
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth使用UserRoleService来获取用户信息
        auth.userDetailsService(userRoleService);
    }


}
