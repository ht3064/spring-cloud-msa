package com.example.userservice.application;

import com.example.userservice.domain.Member;
import com.example.userservice.dto.MemberDto;

public interface MemberService {
    void createMember(MemberDto memberDto);
    MemberDto getMemberByMemberId(String memberId);
    Iterable<Member> getMemberByAll();
}
