package com.hkmc.sample.controller;

import com.hkmc.sample.entity.Address;
import com.hkmc.sample.entity.Member;
import com.hkmc.sample.model.dto.ReqMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MemberControllerTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    @DisplayName("modelMapper 동작확인")
    public void test_modelMapper() throws Exception {
        //given
        Member member=Member.builder()
                .name("lim")
                .address(new Address("seoul", "suji", "6666"))
                .build();

        //when
        ReqMember reqMember = modelMapper.map(member,ReqMember.class);

        //then
        assertEquals(member.getName(),reqMember.getName());
    }
}