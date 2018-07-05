package com.cafe24.mammoth;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cafe24.mammoth.oauth2.api.Scripttags;
import com.cafe24.mammoth.oauth2.api.impl.ScriptTagsTemplate;

/**
 * 
 * 2018-07-05 테스트 완료.
 * @author qyuee
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScriptTagsTemplateTest {
	
	@Test
	public void contextLoads() throws IOException {
		// getList -> ok!
		ScriptTagsTemplate scriptTagsTemplate = new ScriptTagsTemplate("VwWNLZDjGNIr1skNMa0gqA");
		List<Scripttags> list = scriptTagsTemplate.getList();
		
		System.out.println("=========================================");
		for(Scripttags scripttags : list) {
			System.out.println(scripttags);
		}
		System.out.println("=========================================");
		String scriptNo = list.get(0).getScriptNo();
		System.out.println(scriptNo);
		
		// Get -> ok!
		Scripttags s1 = scriptTagsTemplate.get(scriptNo);
		System.out.println(s1);
		
		// count -> ok!
		int count = scriptTagsTemplate.count();
		System.out.println("count : "+count);
		
		// delete -> ok!
		Scripttags delete = scriptTagsTemplate.delete(scriptNo);
		System.out.println(delete.getScriptNo());
		
		String src = "https://lee33397.cafe24.com/securityOAuth2/test.js";
		
		Set<String> displayLocation = new HashSet<String>();
		displayLocation.add("MAIN");
		
		Set<String> skinNo = new HashSet<String>();
		//skinNo.add("3");
		//skinNo.add("4");
		skinNo.add("5");
		
		// create -> ok!
		//================== 위의 데이터를 모두 @RequestBody로 Scripttags 객체에 담았다고 치고.
		Scripttags scripttags = new Scripttags();
		scripttags.setDisplayLocation(displayLocation);
		scripttags.setSkinNo(skinNo);
		scripttags.setSrc(src);
		
		scriptTagsTemplate.create(scripttags);
		
		// update -> ok! 
		/*Scripttags updateValue = new Scripttags();
		displayLocation.add("PRODUCT_LIST");      // PRODUCT_DETAILS 추가
		updateValue.setDisplayLocation(displayLocation);
		
		skinNo.add("5");            // 5번 추가
		updateValue.setSkinNo(skinNo);
		
		src = "https://lee33397.cafe24.com/securityOAuth2/helloScriptTags.js";
		src = "https://test.com/testtest.js";
		updateValue.setSrc(src);  // 변경
		scriptTagsTemplate.update("1530720521366941", updateValue);*/
	}
}
