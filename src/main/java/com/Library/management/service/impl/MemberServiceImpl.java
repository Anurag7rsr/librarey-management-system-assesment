package com.Library.management.service.impl;

import com.Library.management.dto.MemberRequest;
import com.Library.management.entity.Member;
import com.Library.management.exception.ResourceNotFoundException;
import com.Library.management.repository.MemberRepository;
import com.Library.management.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Member register(MemberRequest req) {
        log.info("Registering member email={}", req.getEmail());
        // optionally check duplicate email before saving (controller also checks)
        Member m = new Member();
        m.setName(req.getName());
        m.setEmail(req.getEmail());
        m.setPassword(passwordEncoder.encode(req.getPassword())); // encode password
        m.setRole(req.getRole() == null ? "USER" : req.getRole());
        Member saved = repo.save(m);
        log.debug("Member registered id={}", saved.getId());
        return saved;
    }

    @Override
    public List<Member> listAll() {
        log.debug("Listing all members");
        return repo.findAll();
    }

    @Override
    public Member getById(Long id) {
        log.debug("Fetching member id={}", id);
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
    }
}