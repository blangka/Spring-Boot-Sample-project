package com.hkmc.sample.service;

import com.hkmc.sample.common.error.ApiException;
import com.hkmc.sample.common.error.ExceptionEnum;
import com.hkmc.sample.entity.RefreshToken;
import com.hkmc.sample.entity.User;
import com.hkmc.sample.model.ResJson;
import com.hkmc.sample.model.dto.security.ReqUser;
import com.hkmc.sample.model.dto.security.ResToken;
import com.hkmc.sample.model.dto.security.ResUser;
import com.hkmc.sample.repo.jpa.RefreshTokenRepository;
import com.hkmc.sample.repo.jpa.UserRepository;
import com.hkmc.sample.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signup(ReqUser reqUser) {

        User user = new User().toEntityAndPwdEncoder(reqUser,passwordEncoder);
        userRepository.save(user);

        return user.getId();
    }

    @Transactional
    public ResToken login(ReqUser reqUser) {

        User user = userRepository.findByAccount(reqUser.getAccount()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER));

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = user.toAuthentication(reqUser.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // TODO: 토큰 발행 에러
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        ResToken resToken = jwtUtil.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .tokenKey(authentication.getName())
                .tokenValue(resToken.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return resToken;
    }
}
