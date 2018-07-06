package com.cafe24.mammoth.oauth2.api;

import java.util.List;

public interface ThemesOperations {
	List<Themes> getList();
	
	Themes get(String skinNo);
}
