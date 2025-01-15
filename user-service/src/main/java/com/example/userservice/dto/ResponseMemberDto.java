package com.example.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMemberDto {
    private String email;
    private String name;
    private String memberId;

    private List<ResponseOrderDto> orders;
}
