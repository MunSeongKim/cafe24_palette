package com.cafe24.mammoth;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cafe24.mammoth.oauth2.api.Themes;
import com.cafe24.mammoth.oauth2.api.impl.Cafe24Template;
import com.cafe24.mammoth.oauth2.api.impl.ThemesTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThemeTemplateTest {
	
	@Autowired
	Cafe24Template cafe24Template;
	
	// 0.03s
	@Test
	public void themesGetTest() throws IOException {
		//Cafe24Template cafe24Template = new Cafe24Template("EqtlfWleeJo10yvVp3Phmc");
		//ThemesOperations themesTemplate = cafe24Template.themesOperations();
		ThemesTemplate themesTemplate = cafe24Template.getOperation(ThemesTemplate.class);
		Themes themes = themesTemplate.get("1");
		System.out.println("result : "+themes);
		System.out.println(themes);
	}
	
	// 0.5s
	@Test
	public void themesGetListTest() {
		//Cafe24Template cafe24Template = new Cafe24Template("EqtlfWleeJo10yvVp3Phmc");
		//ThemesOperations themesTemplate = cafe24Template.themesOperations();
		ThemesTemplate themesTemplate = cafe24Template.getOperation(ThemesTemplate.class);
		List<Themes> list = themesTemplate.getList();
		for(Themes themes : list) {
			System.out.println(themes);
		}
	}
}
