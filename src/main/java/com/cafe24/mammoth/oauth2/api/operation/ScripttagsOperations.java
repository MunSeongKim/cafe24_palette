package com.cafe24.mammoth.oauth2.api.operation;

import java.io.IOException;
import java.util.List;

import com.cafe24.mammoth.oauth2.api.Scripttags;

/**
 * Spring social Facebook의 API 사용법을 따라 작성함.<br>
 * 
 * ScriptTagsTemplate가 이 인터페이스를 구현한다.<br>
 * 
 * @author qyuee
 * @since 2018-07-05
 */
public interface ScripttagsOperations{
	
	Scripttags create(Scripttags scripttags);
	
	Scripttags get(String scriptNo);
	
	List<Scripttags> getList() throws IOException;
	
	Scripttags updateDeprecated(String scriptNo, Scripttags scripttags);
	
	boolean update(String scriptNo, Scripttags scripttags);
	
	Scripttags deleteDeprecated(String scriptNo);
	
	boolean delete(String scriptNo);
	
	int count();
}
