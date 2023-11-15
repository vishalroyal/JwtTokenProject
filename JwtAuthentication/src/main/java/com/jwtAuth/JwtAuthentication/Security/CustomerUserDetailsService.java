package com.jwtAuth.JwtAuthentication.Security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        if(userName.equalsIgnoreCase("Vishal"))
        {
            return new User("Vishal","gublya@123",new ArrayList<>());
        }
        else
        {
            throw new UsernameNotFoundException("User doesnt exist");
        }
    }
}
