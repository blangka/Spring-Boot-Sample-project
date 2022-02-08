package com.hkmc.sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 자동을 createBy createDate 넣게 해주기 위해 audit을 위해 추가
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
