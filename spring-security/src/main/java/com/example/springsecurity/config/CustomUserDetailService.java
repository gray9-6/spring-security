package com.example.springsecurity.config;

import com.example.springsecurity.entity.Person;
import com.example.springsecurity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Person person = personRepository.findByUserName(username);

        if(person == null){
            throw new RuntimeException("User doesn't exist");
        }

        return new UserDetailsCreator(person);
    }
}
