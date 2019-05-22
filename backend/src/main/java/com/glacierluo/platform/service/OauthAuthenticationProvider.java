package com.glacierluo.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class OauthAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRoleService userRoleService;

    private Logger logger= LoggerFactory.getLogger(getClass());


    /**
     * 自定义验证方式
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //用户名密码校验
        User oauthUserDetails= (User) userRoleService.loadUserByUsername(authentication.getName());
        logger.debug(authentication.getName()+"++++++++++++++++++++++"+authentication.getCredentials());

        if (!oauthUserDetails.getUsername().equals(authentication.getName()) || !oauthUserDetails.getPassword().equals(authentication.getCredentials())){
            throw new BadCredentialsException("用户名或密码错误");
        }
        Collection<? extends GrantedAuthority> authorities=oauthUserDetails.getAuthorities();
        return new UsernamePasswordAuthenticationToken(oauthUserDetails.getUsername(),oauthUserDetails.getPassword(),authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
