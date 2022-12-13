package com.score.live.service.impl;

import com.score.live.entity.CustomUserDetails;
import com.score.live.entity.User;
import com.score.live.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserService implements UserDetailsService {


    private final UserRepository userRepository;

    @Autowired
    public CustomUserService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        CustomUserDetails userDetails= null;

        if (user != null) {
            userDetails= new CustomUserDetails();
            userDetails.setUser(user);

        } else {
            throw new UsernameNotFoundException("User doesn't exist with the name: " + username);
        }
        return userDetails;
    }
}
