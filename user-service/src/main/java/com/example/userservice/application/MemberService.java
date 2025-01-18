package com.example.userservice.application;

import com.example.userservice.domain.Member;
import com.example.userservice.dto.MemberDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    void createMember(MemberDto memberDto);
    MemberDto getMemberByMemberId(String memberId);
    Iterable<Member> getMemberByAll();
}
