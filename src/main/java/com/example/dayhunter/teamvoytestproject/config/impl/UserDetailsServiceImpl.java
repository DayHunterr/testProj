package com.example.dayhunter.teamvoytestproject.config.impl;

import com.example.dayhunter.teamvoytestproject.models.BaseUser;
import com.example.dayhunter.teamvoytestproject.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository baseUserRepository;

    public UserDetailsServiceImpl(UserRepository baseUserRepository) {
        this.baseUserRepository = baseUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BaseUser baseUser = baseUserRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(baseUser.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                baseUser.getEmail(),
                baseUser.getPassword(),
                true,
                true,
                true,
                true,
                grantedAuthorities);
    }
}
