package com.hkmc.sample.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResMember {

    private String id;
    private String name;
    private Address address;

    public class Address {
        private String city;
        private String streat;
        private String zipcode;
    }
}
