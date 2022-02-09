package com.hkmc.sample.repo.jpa;

import com.hkmc.sample.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
}
