package com.Library.management.service;


import com.Library.management.dto.MemberRequest;
import com.Library.management.entity.Member;
import com.Library.management.exception.ResourceNotFoundException;
import com.Library.management.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repo;

    public Member register(MemberRequest req) {
        Member m = new Member();
        m.setName(req.getName());
        m.setEmail(req.getEmail());
        // plain text password (no-security mode). Consider hashing when re-enabling security.
        m.setPassword(req.getPassword());
        m.setRole(req.getRole() == null ? "USER" : req.getRole());
        return repo.save(m);
    }

    public List<Member> listAll() { return repo.findAll(); }

    public Member getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
    }
}