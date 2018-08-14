package com.cafe24.mammoth.app.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Member;
import com.cafe24.mammoth.app.repository.MemberRepository;

/**
 * 앱 사용자를 관리하는 서비스
 * @author MoonStar
 *
 */
@Service
@Transactional
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	/**
	 * 앱 사용자가 가진 도메인 주소들을 저장하기 위한 메소드
	 * @param baseDomain
	 * @param primaryDomain
	 * @param mallUrl
	 * @param mallId
	 * @return 저장 성공 여부
	 */
	public boolean save(String baseDomain, String primaryDomain, String mallUrl, String mallId) {
		Optional<Member> savedMember = memberRepository.findById(mallId);
		Member member = null;
		if ( savedMember.isPresent() ) {
			member = savedMember.get();
			member.setBaseDomain(baseDomain);
			member.setPrimaryDomain(primaryDomain);
			member.setMallUrl(mallUrl);
		}
		
		return member != null ? true : false;    
	}
	
	/**
	 * 앱 사용자 객체를 생성하고 저장하는 메소드
	 * @param mallId
	 * @return 저당 성공 여부
	 */
	public boolean save(String mallId) {
		Member member = new Member();
		member.setMallId(mallId);
		member.setPanelUsed(false);
		return memberRepository.save(member) != null ? true : false;
	}

	public Member getOneByMallUrl(String mallUrl) {
		return memberRepository.findByMallUrl(mallUrl);
	}
	
	public Member getOne(String mallId) {
		Optional<Member> storedMember = memberRepository.findById(mallId);
		if( storedMember.isPresent() ) {
			return storedMember.get();
		}
		return null;
	}

	public boolean isExist(String mallId) {
		return memberRepository.existsById(mallId);
	}	
}
