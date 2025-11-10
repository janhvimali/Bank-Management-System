package com.quantafic.JWTSecurity.Service;

import com.quantafic.JWTSecurity.Model.UserPrincipal;
import com.quantafic.JWTSecurity.Model.Users;
import com.quantafic.JWTSecurity.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Primary
@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String staffId) throws UsernameNotFoundException {

        Users user = repo.findById(staffId).orElseThrow(() -> new RuntimeException("User not found"));
        if(user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(user);
    }
}
