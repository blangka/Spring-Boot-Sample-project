package com.hkmc.sample.model.dto;

import com.hkmc.sample.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResMember {

    private String id;
    private String name;
    private Address address;

    @Data
    public static class Address {
        private String city;
        private String street;
        private String zipcode;
    }

    public static ResMember of(Member member) {
        return new ModelMapper().map(member,ResMember.class);
    }
}
