package com.example.userservice.application;

import com.example.userservice.dao.MemberRepository;
import com.example.userservice.domain.Member;
import com.example.userservice.dto.MemberDto;
import com.example.userservice.dto.ResponseOrderDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username);

        if (member == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(
                member.getEmail(),
                member.getEncryptedPwd(),
                true,
                true,
                true,
                true,
                new ArrayList<>());
    }

    @Override
    public void createMember(MemberDto memberDto) {
        memberDto.setMemberId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Member member = mapper.map(memberDto, Member.class);
        member.setEncryptedPwd(passwordEncoder.encode(memberDto.getPwd()));

        memberRepository.save(member);
    }

    @Override
    public MemberDto getMemberByMemberId(String memberId) {
        Member member = memberRepository.findByMemberId(memberId);

        if (member == null) {
            throw new UsernameNotFoundException("User not found");
        }

        MemberDto memberDto = new ModelMapper().map(member, MemberDto.class);

        List<ResponseOrderDto> orders = new ArrayList<>();
        memberDto.setOrders(orders);

        return memberDto;
    }

    @Override
    public Iterable<Member> getMemberByAll() {
        return memberRepository.findAll();
    }
}
