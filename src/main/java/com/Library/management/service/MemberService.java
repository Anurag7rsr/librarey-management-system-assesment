package com.Library.management.service;

import com.Library.management.dto.MemberRequest;
import com.Library.management.entity.Member;

import java.util.List;

public interface MemberService {
    Member register(MemberRequest req);
    List<Member> listAll();
    Member getById(Long id);
}