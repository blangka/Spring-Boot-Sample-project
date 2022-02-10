package com.hkmc.sample.config;

import com.hkmc.sample.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * 자동을 createBy createDate 넣게 해주기 위해 audit을 위해 추가
 */

@Configuration
@EnableJpaAuditing
public class AudiorConfig {

    @Bean
    public AuditorAware<String> auditorProvider(){
        return new AuditorAwareImpl();
    }

    public static class AuditorAwareImpl implements AuditorAware<String>{
        @Override
        public Optional<String> getCurrentAuditor() {
            if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User){
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //토큰에 User 객체 반환
                return Optional.of(user.getAccount());
            }else{
                return Optional.of("system");
            }
        }
    }
}
