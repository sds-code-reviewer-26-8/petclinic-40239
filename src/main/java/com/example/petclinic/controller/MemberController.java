package com.example.petclinic.controller;

import com.example.petclinic.controller.response.MemberDto;
import com.example.petclinic.entity.Member;
import com.example.petclinic.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<MemberDto> list() {
        return memberService.findMembers().stream()
                .map(m -> new MemberDto(
                        m.getUsername(),
                        m.getEnabled()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{username}")
    public MemberDto detail(@PathVariable String username) {
        Member member = memberService.findMember(username);
        return new MemberDto(
                member.getUsername(),
                member.getEnabled());
    }
}
