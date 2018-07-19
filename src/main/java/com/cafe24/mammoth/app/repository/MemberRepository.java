package com.cafe24.mammoth.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cafe24.mammoth.app.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

	@Query("SELECT m FROM Member m WHERE "
			+ "(m.baseDomain LIKE %:mallUrl%) OR "
			+ "(m.primaryDomain LIKE %:mallUrl%) OR "
			+ "(m.mallUrl LIKE %:mallUrl%)")
	public Member findByMallUrl(@Param(value="mallUrl") String mallUrl);

	@Query("SELECT CASE WHEN COUNT(*) = 1 THEN true ELSE false END FROM Member m WHERE m.mallId = :mallId ")
	public boolean existsById(@Param(value="mallId") String mallId);
	
}
