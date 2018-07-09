package com.cafe24.mammoth.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Script;
import com.cafe24.mammoth.app.repository.ScriptRepository;

@Service
@Transactional
public class ScriptService {
	
	@Autowired
	ScriptRepository repo;

	public Map<String, Script> updateIsApplyByPanelId(Map<String, Object> requestData, Long id) {
		String data = null;

		boolean state = (boolean) requestData.get("state");
		
		
		// repo.updateIsApplyByPanelId(state, id);
		Script script = getScriptByPanelId(id);

		//적용 시 dp_location 데이터 저장
		if (state == true) {
			@SuppressWarnings("unchecked")
			List<String> pages = (List<String>) requestData.get("data");
			data = String.join(",", pages);
			
			script.setDpLocation(data);
		}
		// 스크립트 적용 상태 변경
		script.setIsApply(state);
		// get looping 방지
		script.setPanel(null);
		
		// Finding a script for inactive, if already script has active state
		Script activeScript = getScriptByExceptPanelId(id);
		Map<String, Script> map = new HashMap<>();
		if(activeScript != null) {
			activeScript.setIsApply(false);
			// get looping 방지
			activeScript.setPanel(null);
		}
		
		
		
		map.put("clickChangeState", script);
		map.put("autoChangeState", activeScript != null ? activeScript : null);
		return map;
	}
	
	public Script getScriptByPanelId(Long id) {
		Optional<Script> result = repo.findById(id);
		Script s = null;
		if(result.isPresent()) {
			s = result.get();
		}
		return s;
	}
	
	public Script getScriptByExceptPanelId(Long id) {
		return repo.findByExceptId(id);
	}
	
}
