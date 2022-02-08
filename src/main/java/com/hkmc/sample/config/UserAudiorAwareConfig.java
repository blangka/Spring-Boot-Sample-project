package com.hkmc.sample.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 자동을 createBy createDate 넣게 해주기 위해 audit을 위해 추가
 */

@Component
public class UserAudiorAwareConfig implements AuditorAware<String>{
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("lim");
        /* spring security 추후 연경
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || !authentication.isAuthenticated()) {
            return null;
        }
        User user = (User) authentication.getPrincipal();
        return Optional.of(user.getUserId());*/
    }
}
