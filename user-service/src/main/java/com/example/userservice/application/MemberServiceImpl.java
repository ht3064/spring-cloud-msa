package com.example.userservice.application;

import com.example.userservice.dao.MemberRepository;
import com.example.userservice.domain.Member;
import com.example.userservice.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void createMember(MemberDto memberDto) {
        memberDto.setMemberId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Member member = mapper.map(memberDto, Member.class);
        member.setEncryptedPwd("encrypted_password");

        memberRepository.save(member);
    }
}
