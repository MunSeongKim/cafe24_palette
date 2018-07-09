package com.cafe24.mammoth.app.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Auth;
import com.cafe24.mammoth.app.domain.Member;
import com.cafe24.mammoth.app.repository.MemberRepository;

@Service
@Transactional
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	public boolean save(String mallId, String mallUrl, Auth auth) {
		Member member = new Member();
		member.setMallId(mallId);
		member.setMallUrl(mallUrl);
		member.setAuth(auth);
		return memberRepository.save(member) == null ? false : true;
	}
	
}
