package com.hkmc.sample.service;

import com.hkmc.sample.common.error.ApiException;
import com.hkmc.sample.common.error.ExceptionEnum;
import com.hkmc.sample.entity.User;
import com.hkmc.sample.repo.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER));

        UserDetails userDetails = createUserDetails(user);
        return userDetails;
    }

    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(User user) {

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
