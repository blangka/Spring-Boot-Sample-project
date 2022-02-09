package com.hkmc.sample.service;

import com.hkmc.sample.entity.Member;
import com.hkmc.sample.repo.jpa.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {
    
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    //@Rollback(false)
    @DisplayName("회원 가입이 정상 적으로 되어야 한다")
    public void 회원가입() throws Exception {
        //given
        Member member = Member.builder()
                .name("lim")
                .build();

        //when
        Long savedId = memberService.join(member);

        //then
        //em.flush();
        assertEquals(member, memberRepository.findById(savedId).orElse(null));
    }

    @Test
    @DisplayName("회원은 중복으로 가입되지 않아야 한다.")
    public void 중복_회원_예외() throws Exception {
        //given
        String name = "lim";

        Member member1 = Member.builder()
                .name(name)
                .build();

        Member member2 = Member.builder()
                .name(name)
                .build();

        //when
        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
   }


}