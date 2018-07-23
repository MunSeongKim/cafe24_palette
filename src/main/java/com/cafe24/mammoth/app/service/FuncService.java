package com.cafe24.mammoth.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Func;
import com.cafe24.mammoth.app.repository.FuncRepository;

@Service
@Transactional
public class FuncService {
	
	@Autowired 
	private FuncRepository funcRepository;
	
	public boolean save(Func func) {
		System.out.println(func);
		return funcRepository.save(func) != null ? true : false;
	}
	
	public List<Func> getFuncList(){
		return funcRepository.findAll();
	}
}
