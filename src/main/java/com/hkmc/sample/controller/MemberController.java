package com.hkmc.sample.controller;

import com.hkmc.sample.entity.Member;
import com.hkmc.sample.model.ResJson;
import com.hkmc.sample.model.dto.ReqMember;
import com.hkmc.sample.model.dto.ResId;
import com.hkmc.sample.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
@Api(tags = "회원 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/")
    @ApiOperation(value = "회원 등록")
    public ResJson<ResId> create(@RequestBody ReqMember reqMember) {
        return new ResJson<>(new ResId(memberService.join(Member.of(reqMember))));
    }
}
