package com.cafe24.mammoth.app.support;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
/**
 * Tab과 Tab 기능 구현 jsp 파일 연동 Class
 * @author deo
 *
 */
public class SettingElement {
	private String tabName;		// tab name
	private String jspFile;	// jsp:include에 들어갈 jsp file name
	
	public SettingElement() {	
	}
	public SettingElement(String tabName, String jspFile) {	
		this.tabName = tabName;
		this.jspFile = jspFile;
	}
}
