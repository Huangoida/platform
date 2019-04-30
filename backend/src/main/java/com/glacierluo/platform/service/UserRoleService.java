package com.glacierluo.platform.service;

import com.glacierluo.platform.entity.Role;

import com.glacierluo.platform.entity.UserRole;
import com.glacierluo.platform.repository.RoleRepository;
import com.glacierluo.platform.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


@Service
public class UserRoleService implements UserDetailsService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserRole userRole=userRoleRepository.findByuserId(Long.parseLong(username));
        if (userRole == null){
            //没有找到用户
            throw  new UsernameNotFoundException(username + "not found");
        }

        Collection<GrantedAuthority> grantedAuthorities=new ArrayList<>();


        Optional<Role> role=roleRepository.findById(userRole.getRoleId());
        GrantedAuthority grantedAuthority=new SimpleGrantedAuthority(role.get().getName());
        grantedAuthorities.add(grantedAuthority);

        //返回一个SpringSecurity需要的用户对象
        return new User(userRole.getUserId().toString(),userRole.getPassword(),grantedAuthorities);
    }
}
