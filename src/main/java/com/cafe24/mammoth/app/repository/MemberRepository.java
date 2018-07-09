package com.cafe24.mammoth.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe24.mammoth.app.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
