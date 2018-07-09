package com.cafe24.mammoth.oauth2.api.operation;

import java.util.List;

import com.cafe24.mammoth.oauth2.api.Themes;

public interface ThemesOperations {
	List<Themes> getList();
	
	Themes get(String skinNo);
}
