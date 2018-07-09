package com.cafe24.mammoth.app.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.repository.MemberRepository;

@Service
@Transactional
public class MemberService {
	@Autowired
	MemberRepository repo;
	

}
