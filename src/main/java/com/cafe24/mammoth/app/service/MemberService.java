package com.cafe24.mammoth.app.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Member;
import com.cafe24.mammoth.app.repository.MemberRepository;

@Service
@Transactional
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	public boolean save(String baseDomain, String primaryDomain, String mallUrl, String mallId) {
		Optional<Member> savedMember = memberRepository.findById(mallId);
		Member member = null;
		if ( savedMember.isPresent() ) {
			member = savedMember.get();
			member.setPanelUsed(false);
			member.setBaseDomain(baseDomain);
			member.setPrimaryDomain(primaryDomain);
			member.setMallUrl(mallUrl);
		}
		
		return member != null ? true : false;    
	}
	
	public boolean save(String mallId) {
		Member member = new Member();
		member.setMallId(mallId);
		System.out.println("memberService.save(): " + member);
		return memberRepository.save(member) != null ? true : false;
	}

	public Member getOneByMallUrl(String mallUrl) {
		return memberRepository.findByMallUrl(mallUrl);
	}

	public boolean isExist(String mallId) {
		return memberRepository.existsById(mallId);
	}	
}
