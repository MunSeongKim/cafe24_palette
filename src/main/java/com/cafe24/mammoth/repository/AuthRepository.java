package com.cafe24.mammoth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafe24.mammoth.domain.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, String>{
	
}
