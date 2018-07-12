package com.cafe24.mammoth.app.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Script;
import com.cafe24.mammoth.app.repository.ScriptRepository;
import com.cafe24.mammoth.oauth2.api.Scripttags;
import com.cafe24.mammoth.oauth2.api.Themes;
import com.cafe24.mammoth.oauth2.api.impl.Cafe24Template;
import com.cafe24.mammoth.oauth2.api.impl.ScripttagsTemplate;
import com.cafe24.mammoth.oauth2.api.impl.ThemesTemplate;

@Service
@Transactional
public class ScriptService {

	@Autowired
	ScriptRepository scriptRepository;

	@Autowired
	Cafe24Template cafe24Template;

	/**
	 * Scripttags API 사용하여 실제 적용 구현<br>
	 * 
	 * @since 2018-07-11
	 * @author MoonStar
	 * @param requestData
	 *            - display_location, state 정보
	 * @param id
	 *            - panel id
	 * @return Map<String, Script> - 변경된 상태 정보
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Script> applyPanel(Map<String, Object> requestData, Long panelId) {
		// state = 현재 패널의 적용 상태, true: 적용되지 않았음, false: 적용 됨
		Boolean state = (Boolean) requestData.get("state");

		// 패널 적용.
		if (state) {
			Scripttags scripttags = new Scripttags();
			
			// Theme들의 skin_no 목록 구해서 Set으로 변환
			List<Themes> themes = cafe24Template.getOperation(ThemesTemplate.class).getList();
			Set<String> skinNo = new HashSet<>();
			for (Themes t : themes) {
				skinNo.add(t.getSkinNo());
			}
			// skin_no setting
			scripttags.setSkinNo(skinNo);
			
			// 적용할 displayLocation 추출
			List<String> datas = (List<String>) requestData.get("data");
			Set<String> displayLocation = new HashSet<>();
			for (String data : datas) {
				displayLocation.add(data);
			}
			// display_location setting
			scripttags.setDisplayLocation(displayLocation);
			
			ScripttagsTemplate scripttagsTemplate = cafe24Template.getOperation(ScripttagsTemplate.class);
			
			// 패널의 스크립트 적용에 관한 데이터 변경
			Script appliedScript = scriptRepository.findByApplied();
			String scripttagsNo;
			// 이미 적용 된 상태에서 적용할 경우
			if (appliedScript != null) {
				scripttagsNo = appliedScript.getScripttagsNo();
				scripttagsTemplate.update(scripttagsNo, scripttags);
				appliedScript.setScripttagsNo(null);
				appliedScript.setIsApply(false);
			}
			// 적용 된 패널이 없을 때 적용 할 경우
			else {
				scripttags = scripttagsTemplate.create(scripttags);
				scripttagsNo = scripttags.getScriptNo();
			}

			// API 적용 후 DB에 데이터 저장
			Optional<Script> script = scriptRepository.findById(panelId);
			Script applyScript = null;
			if( script.isPresent() ) {
				applyScript = script.get();
				String data = String.join(",", datas);
				// 스크립트 적용 상태 변경
				applyScript.setDpLocation(data);
				applyScript.setIsApply(true);
			}
			
			Map<String, Script> result = new HashMap<>();
			result.put("clickChangeState", applyScript);
			result.put("authChangeState", appliedScript != null ? appliedScript : null);
			return result;
		}

		// 패널 적용 해제.
		ScripttagsTemplate scripttagsTemplate = cafe24Template.getOperation(ScripttagsTemplate.class);
		Optional<Script> script = scriptRepository.findById(panelId);
		Script unapplyScript = script.isPresent() ? script.get() : null;
		
		scripttagsTemplate.delete(unapplyScript.getScripttagsNo());
		unapplyScript.setIsApply(false);
		unapplyScript.setScripttagsNo(null);
		
		Map<String, Script> result = new HashMap<>();
		result.put("clickChangeState", unapplyScript);
		result.put("authChangeState", null);
		return result;
	}

}
