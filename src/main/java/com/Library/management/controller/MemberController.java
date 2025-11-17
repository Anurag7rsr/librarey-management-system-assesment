package com.Library.management.controller;

import com.Library.management.dto.MemberResponse;
import com.Library.management.entity.Member;
import com.Library.management.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members")
    public List<MemberResponse> listAll() {
        return memberService.listAll().stream()
                .map(m -> new MemberResponse(m.getId(), m.getName(), m.getEmail(), m.getRole(), m.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @GetMapping("/members/{id}")
    public MemberResponse getById(@PathVariable Long id) {
        Member m = memberService.getById(id);
        return new MemberResponse(m.getId(), m.getName(), m.getEmail(), m.getRole(), m.getCreatedAt());
    }
}
