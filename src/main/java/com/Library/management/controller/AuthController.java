package com.Library.management.controller;

import com.Library.management.dto.LoginRequest;
import com.Library.management.dto.MemberRequest;
import com.Library.management.entity.Member;
import com.Library.management.repository.MemberRepository;
import com.Library.management.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final MemberRepository memberRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody MemberRequest req) {
        if (memberRepo.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        Member saved = memberService.register(req);
        saved.setPassword(null);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        var opt = memberRepo.findByEmail(req.getEmail());
        if (opt.isEmpty()) return ResponseEntity.status(401).body("Invalid credentials");

        Member m = opt.get();

        if (!passwordEncoder.matches(req.getPassword(), m.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        m.setPassword(null);
        return ResponseEntity.ok(m);
    }
}
