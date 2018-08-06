package com.cafe24.mammoth;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cafe24.mammoth.oauth2.api.Scripttags;
import com.cafe24.mammoth.oauth2.api.impl.Cafe24Template;
import com.cafe24.mammoth.oauth2.api.impl.ScripttagsTemplate;
import com.cafe24.mammoth.oauth2.api.operation.ScripttagsOperations;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScriptTagsTemplateTest {
	
	@Autowired
	Cafe24Template cafe24Template;
	
	@SuppressWarnings("unused")
	private static final String accessToken = "UUgDC2P2BNWhOIYfxOuxMG";
	/*private Cafe24Template cafe24Template = new Cafe24Template(accessToken);*/
	
	private ScripttagsTemplate scripttagsTemplate;
	
	// getList -> ok!
	@Test
	//@Ignore
	public void scripttagsGetListTest() throws IOException {
		cafe24Template.setAccessToken(accessToken);
		cafe24Template.setAccessToken("chyin370");
		scripttagsTemplate = cafe24Template.getOperation(ScripttagsTemplate.class);
		List<Scripttags> list = scripttagsTemplate.getList();
		for(Scripttags scripttags : list) {
			System.out.println(scripttags);
		}
	}
	
	// Get -> ok!
	@Test
	@Ignore
	public void scripttagsGetTest() {
		Scripttags s1 = scripttagsTemplate.get("");
		System.out.println(s1);
	}
	
	// count -> ok!
	@Test
	@Ignore
	public void scripttagsCountTest() {
		int count = scripttagsTemplate.count();
		System.out.println("count : "+count);
	}
	
	// delete -> ok!
	@Test
	@Ignore
	public void scripttagsDeleteTest() {
		boolean result = scripttagsTemplate.delete("1530862881319004");
		System.out.println("삭제 결과 : "+result);
	}
	
	// create -> ok!
	@Test
	//@Ignore
	public void scripttagsCreateTest() {
		String src = "https://lee33397.cafe24.com/securityOAuth2/test.js";
		Set<String> displayLocation = new HashSet<String>();
		displayLocation.add("MAIN");
		
		Set<String> skinNo = new HashSet<String>();
		skinNo.add("3");
		skinNo.add("4");
		skinNo.add("6");
		
		//================== 위의 데이터를 모두 @RequestBody로 Scripttags 객체에 담았다고 치고.
		Scripttags scripttags = new Scripttags();
		scripttags.setDisplayLocation(displayLocation);
		scripttags.setSkinNo(skinNo);
		scripttags.setSrc(src);
		
		Scripttags resultScripttags = scripttagsTemplate.create(scripttags);
		
		System.out.println("===================[create result]===================");
		try {
			System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(resultScripttags));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("=====================================================");
	}
	
	@Test
	@Ignore
	public void scripttagsUpdateTest() {
		// update -> ok!
		Set<String> displayLocation = new HashSet<String>();
		Set<String> skinNo = new HashSet<String>();
		
		Scripttags updateValue = new Scripttags();
		displayLocation.add("PRODUCT_LIST");      // PRODUCT_DETAILS 추가
		updateValue.setDisplayLocation(displayLocation);
		
		skinNo.add("5");               // 5번 추가
		updateValue.setSkinNo(skinNo);
		
		String src = "https://lee33397.cafe24.com/securityOAuth2/helloScriptTags.js";
		updateValue.setSrc(src);    // 변경
		
		boolean result = scripttagsTemplate.update("1530862601642794", updateValue);
		System.out.println("수정 결과 : "+result);
	}
}
