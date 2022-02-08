package com.hkmc.sample.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hkmc.sample.model.dto.ReqMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member extends BaseEntity{

    @Id @GeneratedValue //key 생성
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member") //연관관계의 거울이다 mappedBy, 읽기 적용
    private List<Order> orders = new ArrayList<>();

    public static Member of(ReqMember reqMember) {
        return Member.builder()
                .name(reqMember.getName())
                .address(new Address(reqMember.getCity(), reqMember.getStreet(), reqMember.getZipcode()))
                .build();
    }

}
