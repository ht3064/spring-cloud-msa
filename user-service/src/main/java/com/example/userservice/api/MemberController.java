package com.example.userservice.api;

import com.example.userservice.application.MemberService;
import com.example.userservice.dto.MemberDto;
import com.example.userservice.dto.RequestMemberDto;
import com.example.userservice.dto.ResponseMemberDto;
import com.example.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private Environment env;
    private final MemberService memberService;

    @Autowired
    private Greeting greeting;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service.";
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

        ResponseMemberDto responseMemberDto = mapper.map(requestMemberDto, ResponseMemberDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMemberDto);
    }
}
