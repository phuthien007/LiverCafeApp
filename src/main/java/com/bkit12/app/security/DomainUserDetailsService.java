package com.bkit12.app.security;

import java.util.Locale;

import com.bkit12.app.domain.Users;
import com.bkit12.app.domain.enumeration.UserStatus;
import com.bkit12.app.repository.UsersRepository;
import java.util.*;
import java.util.stream.Collectors;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UsersRepository usersRepository;

    public DomainUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Authenticating {}", username);
        if(new EmailValidator().isValid(username, null)){
            return usersRepository.findOneByUsername(username)
            .map(user -> createSpringSecurityUser(username, user))
            .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " was not found in the database"));
        }

        String lowercaseLogin = username.toLowerCase();
        return usersRepository
        .findOneByUsername(lowercaseLogin)
        .map(user -> createSpringSecurityUser(lowercaseLogin, user))
        .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }

    private User createSpringSecurityUser(String lowercaseLogin, Users user){
        // if(!user.getStatus().toString().equals(UserStatus.LOCKED.toString())){
        //     throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        // }

        List<GrantedAuthority>  grantedAuthority = user
        .getRoles()
        .stream()
        .map(role -> new SimpleGrantedAuthority(role.getType().toString()))
        .collect(Collectors.toList());

        return new User(user.getUsername(), user.getPassword(), grantedAuthority);
    }
    
    
}
