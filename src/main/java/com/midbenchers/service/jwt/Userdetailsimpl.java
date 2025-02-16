package com.midbenchers.service.jwt;

import com.midbenchers.entity.User;
import com.midbenchers.repository.Userrepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class Userdetailsimpl implements UserDetailsService {


    private final Userrepo userrepo;

    public Userdetailsimpl(Userrepo userrepo) {
        this.userrepo = userrepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


       Optional<User> user= userrepo.findByEmail(username);

       if(user==null || user.isEmpty()){
           throw  new UsernameNotFoundException("user not found :"+username);
       }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(),user.get().getPassword(),
                new ArrayList<>()) {
        };
    }
}
