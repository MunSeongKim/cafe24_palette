package com.cafe24.mammoth.app.support;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString 
/**
 * TAB 동적 생성 Class
 * 
 * @author deo
 *
 */
public class SettingTab {
	private Map<Integer, SettingElement> map = new HashMap<>();

	public SettingTab() { 
		map.put(1, new SettingElement("유형 설정", "type"));
		map.put(2, new SettingElement("기능 선택", "func")); 
		map.put(3, new SettingElement("테마 설정", "theme")); 
	}
} 
