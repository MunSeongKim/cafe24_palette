package com.cafe24.mammoth.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafe24.mammoth.oauth2.domain.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, String>{
	
}
