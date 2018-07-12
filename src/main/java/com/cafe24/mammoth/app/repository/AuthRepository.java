package com.cafe24.mammoth.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cafe24.mammoth.app.domain.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, String>{
	public Auth findByMallId(String mallId);
	
	@Query("select a from Auth a where a.mallId=:id")
	public Auth findOne(@Param("id") String mallId);
}
