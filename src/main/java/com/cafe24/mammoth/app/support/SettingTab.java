package com.cafe24.mammoth.app.support;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString 
public class SettingTab {
	private Map<Integer, SettingElement> map = new HashMap<>();
	
	public SettingTab() {
		map.put(1, new SettingElement("기능 선택", "func"));
		map.put(2, new SettingElement("테마 설정", "theme"));
		map.put(3, new SettingElement("디자인 설정", "design")); 
		/*map.put(4, new SettingElement("동작 설정", "action"));*/
	}
} 
