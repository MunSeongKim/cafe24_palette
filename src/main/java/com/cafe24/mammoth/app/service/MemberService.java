package com.cafe24.mammoth.app.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Auth;
import com.cafe24.mammoth.app.domain.Member;
import com.cafe24.mammoth.app.repository.AuthRepository;
import com.cafe24.mammoth.app.repository.MemberRepository;

@Service
@Transactional
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private AuthRepository authRepository;
	
	public boolean save(String mallUrl, String mallId) {
		Optional<Member> savedMember = memberRepository.findById(mallId);
		Member member;
		
		if ( !savedMember.isPresent() ) {
			member = new Member();
		} else {
			member = savedMember.get();
		}
		
		Auth auth = authRepository.findOne(mallId);
		member.setPanelUsed(false);
		member.setMallUrl(mallUrl);
		member.setAuth(auth);
		
		return memberRepository.save(member) == null ? false : true;
	}	
}
