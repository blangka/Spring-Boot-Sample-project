package com.hkmc.sample.controller;

import com.hkmc.sample.entity.Member;
import com.hkmc.sample.model.dto.ReqMember;
import com.hkmc.sample.model.dto.ResMember;
import com.hkmc.sample.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new ReqMember());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid ReqMember ReqMember, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = Member.of(ReqMember);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {

        List<ResMember> resMembers =memberService.findMembersMappingResMember();
        model.addAttribute("members", resMembers);
        return "members/memberList";
    }

}
