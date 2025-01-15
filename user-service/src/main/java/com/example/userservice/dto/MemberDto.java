package com.example.userservice.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MemberDto {
    private String email;
    private String name;
    private String pwd;
    private String memberId;
    private Date createdAt;
    private String encryptedPwd;

    private List<ResponseOrderDto> orders;
}
