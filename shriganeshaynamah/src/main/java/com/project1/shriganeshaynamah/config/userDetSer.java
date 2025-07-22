package com.project1.shriganeshaynamah.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project1.shriganeshaynamah.Dao.userDao;
import com.project1.shriganeshaynamah.user.User;
@Service
public class userDetSer implements UserDetailsService{
    @Autowired
    private userDao use;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User us=use.findByName(username);
        if(us==null){
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("unable to find User");
        }
        
        return new Customuser(us);
    }
    
}
