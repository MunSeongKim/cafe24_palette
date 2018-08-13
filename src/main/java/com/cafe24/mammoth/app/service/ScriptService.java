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
		System.out.println("ScriptService -------------------------------------------------");
		
		// state = 현재 패널의 적용 상태, true: 적용되지 않았음, false: 적용 됨
		Boolean state = (Boolean) requestData.get("state");

		// 패널 적용.
		if (state) {
			Scripttags requestScripttags = new Scripttags();
			
			// Theme들의 skin_no 목록 구해서 Set으로 변환
			List<Themes> themes = cafe24Template.getOperation(ThemesTemplate.class).getList("pc");
			Set<String> skinNo = new HashSet<>();
			for (Themes t : themes) {
				skinNo.add(t.getSkinNo());
			}
			
			themes = cafe24Template.getOperation(ThemesTemplate.class).getList("mobile");
			for (Themes t : themes) {
				skinNo.add(t.getSkinNo());
			}

			// skin_no setting
			requestScripttags.setSkinNo(skinNo);
			
			// 적용할 displayLocation 추출
			List<String> datas = (List<String>) requestData.get("data");
			Set<String> displayLocation = new HashSet<>();
			for (String data : datas) {
				displayLocation.add(data);
			}
			// display_location setting
			requestScripttags.setDisplayLocation(displayLocation);
			
			ScripttagsTemplate scripttagsTemplate = cafe24Template.getOperation(ScripttagsTemplate.class);
			
			// 패널의 스크립트 적용에 관한 데이터 변경
//			Script appliedScript = scriptRepository.findByApplied(panelId);
			String scripttagsNo;
			
			Optional<Script> script = scriptRepository.findById(panelId);
			Script savedScript = script.isPresent() ? script.get() : null;
			
			requestScripttags.setSrc( "https://devbit005.cafe24.com/mammoth" + savedScript.getFilepath() );
			
			
			// 이미 적용 된 상태에서 적용할 경우
//			if (appliedScript != null) {
//				scripttagsNo = appliedScript.getScripttagsNo();
//				scripttagsTemplate.update(scripttagsNo, requestScripttags);
//				appliedScript.setScripttagsNo(null);
//				appliedScript.setIsApply(false);
//			}
			// 적용 된 패널이 없을 때 적용 할 경우
//			else {
				Scripttags tmp = scripttagsTemplate.create(requestScripttags);
				scripttagsNo = tmp.getScriptNo();
//			}

			
			// API 적용 후 DB에 데이터 저장
			String data = String.join(",", datas);
			// 스크립트 적용 상태 변경
			savedScript.setDpLocation(data);
			savedScript.setIsApply(true);
			savedScript.setScripttagsNo(scripttagsNo);
			savedScript.getPanel().getMember().setPanelUsed(true);
			
			Script clickChangeState = new Script();
			Script autoChangeState = new Script();
			
			clickChangeState.setPanelId(savedScript.getPanelId());
			clickChangeState.setIsApply(savedScript.getIsApply());
//			if(appliedScript != null) {
//				autoChangeState.setPanelId(appliedScript.getPanelId());
//				autoChangeState.setIsApply(appliedScript.getIsApply());
//			} else {
				autoChangeState = null;
//			}
			
			
			Map<String, Script> result = new HashMap<>();
			result.put("clickChangeState", clickChangeState);
			result.put("autoChangeState", autoChangeState );
			return result;
		}

		// 패널 적용 해제.
		ScripttagsTemplate scripttagsTemplate = cafe24Template.getOperation(ScripttagsTemplate.class);
		Optional<Script> script = scriptRepository.findById(panelId);
		Script unapplyScript = script.isPresent() ? script.get() : null;
		
		unapplyScript.getPanel().getMember().setPanelUsed(false);
		
		scripttagsTemplate.delete(unapplyScript.getScripttagsNo());
		unapplyScript.setIsApply(false);
		unapplyScript.setScripttagsNo(null);
		
		Script clickChangeState = new Script();
		clickChangeState.setPanelId(unapplyScript.getPanelId());
		clickChangeState.setIsApply(unapplyScript.getIsApply());
		
		Map<String, Script> result = new HashMap<>();
		result.put("clickChangeState", clickChangeState);
		result.put("autoChangeState", null);
		return result;
	}

}
