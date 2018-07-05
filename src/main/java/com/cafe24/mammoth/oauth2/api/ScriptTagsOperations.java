package com.cafe24.mammoth.oauth2.api;

import java.io.IOException;
import java.util.List;

/**
 * Spring social Facebook의 API 사용법을 따라 작성함.<br>
 * 
 * ScriptTagsTemplate가 이 인터페이스를 구현한다.<br>
 * 
 * @author qyuee
 * @since 2018-07-05
 */
public interface ScriptTagsOperations {
	
	Scripttags create(Scripttags scripttags);
	
	Scripttags get(String scriptNo);
	
	List<Scripttags> getList() throws IOException;
	
	Scripttags update(String scriptNo, Scripttags scripttags);
	
	Scripttags delete(String scriptNo);
	
	int count();
}
