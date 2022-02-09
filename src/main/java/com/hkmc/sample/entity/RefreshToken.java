package com.hkmc.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "refresh_token")
@Getter
public class RefreshToken {

    @Id
    @GeneratedValue
    @Column(name = "refresh_token_id")
    private Long id;

    private String tokenKey;

    private String tokenValue;

    public RefreshToken updateValue(String token) {
        this.tokenValue = token;
        return this;
    }
}
