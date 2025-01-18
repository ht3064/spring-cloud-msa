package com.example.userservice.api;

import com.example.userservice.application.MemberService;
import com.example.userservice.domain.Member;
import com.example.userservice.dto.MemberDto;
import com.example.userservice.dto.RequestMemberDto;
import com.example.userservice.dto.ResponseMemberDto;
import com.example.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MemberController {
    private final Environment env;
    private final MemberService memberService;
    private final Greeting greeting;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service on PORT %S", env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/members")
    public ResponseEntity<ResponseMemberDto> createMember(@RequestBody RequestMemberDto requestMemberDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        MemberDto memberDto = mapper.map(requestMemberDto, MemberDto.class);
        memberService.createMember(memberDto);

        ResponseMemberDto responseMemberDto = mapper.map(memberDto, ResponseMemberDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMemberDto);
    }

    @GetMapping("/members")
    public ResponseEntity<List<ResponseMemberDto>> getMembers() {
        Iterable<Member> memberList = memberService.getMemberByAll();

        List<ResponseMemberDto> result = new ArrayList<>();
        memberList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseMemberDto.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<ResponseMemberDto> getMember(@PathVariable("memberId") String memberId) {
        MemberDto memberDto = memberService.getMemberByMemberId(memberId);

        ResponseMemberDto returnValue = new ModelMapper().map(memberDto, ResponseMemberDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
