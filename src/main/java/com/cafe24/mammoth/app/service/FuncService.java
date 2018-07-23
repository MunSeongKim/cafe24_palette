package com.cafe24.mammoth.app.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Func;
import com.cafe24.mammoth.app.repository.FuncRepository;

@Service
@Transactional
public class FuncService {
	private FuncRepository funcRepository;
	
	public boolean save(Func func) {
		System.out.println(func);
		return funcRepository.save(func) != null ? true : false;
	}
}
