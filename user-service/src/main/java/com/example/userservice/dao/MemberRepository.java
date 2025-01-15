package com.example.userservice.dao;

import com.example.userservice.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByMemberId(String memberId);
}
