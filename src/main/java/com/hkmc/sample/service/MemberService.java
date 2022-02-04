package com.hkmc.sample.service;

import com.hkmc.sample.entity.Member;
import com.hkmc.sample.common.error.ExceptionEnum;
import com.hkmc.sample.common.error.ApiException;
import com.hkmc.sample.repo.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)  // 조회시에는 readOnly true 가 성능이 더 잘나온다. 쓰기나 변경이 필요한 경우 @Transactional을 추가
@RequiredArgsConstructor //@RequiredArgsConstructor 초기화 되지 않은 final 필드와 @NonNull 어노테이션이 붙은 필드에 대한 생성자 생성, @AllArgsConstructor 모든 필드에 대한 생성자 생성.
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 모든 회원 검색
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 1명 검색
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    /**
     * 회원 중복 검증
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new ApiException(ExceptionEnum.DUPLICATE_MEMBER);
        }
    }
}
