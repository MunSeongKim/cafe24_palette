package com.cafe24.mammoth.oauth2.api.impl;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cafe24.mammoth.oauth2.api.ScriptTagsOperations;
import com.cafe24.mammoth.oauth2.api.Scripttags;
import com.cafe24.mammoth.oauth2.api.impl.json.Cafe24ApiJsonParser;
import com.cafe24.mammoth.oauth2.api.support.URIBuilder;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * scripttags API를 사용하기 위한 restTemplate.<br>
 * 
 * [메소드 목록]<br>
 * 1. List all Scripttags -> {@link ScriptTagsTemplate#getList()} <br>
 * 2. Count all Scripttags -> {@link ScriptTagsTemplate#count()} <br>
 * 3. Get a Scripttags -> {@link ScriptTagsTemplate#get(String)} <br>
 * 4. Create a Scripttags -> {@link ScriptTagsTemplate#create(Scripttags)} <br>
 * 5. Update a Scripttags -> {@link ScriptTagsTemplate#update(String, Scripttags)} <br>
 * 6. Delete a Scripttags -> {@link ScriptTagsTemplate#delete(String)} <br>
 * 
 * @version 1.0
 * @since 2018-07-03
 * @author qyuee
 *
 */
public class ScriptTagsTemplate implements ScriptTagsOperations{
	
	private static final String SCRIPTTAGS_PATH = "/api/v2/admin/scripttags";
	private RestTemplate usingApiRestTemplate;
	private URI apiUrl;
	private ObjectMapper objectMapper;
	
	public ScriptTagsTemplate(RestTemplate usingApiRestTemplate) {
		objectMapper = new ObjectMapper();
		this.usingApiRestTemplate = usingApiRestTemplate;
	}
	
	/**
	 * 사용자로 부터 view단에서 src, display_location, skin_no를 받아 @Requestbody로 POJO(Scripttags)에 매핑한 뒤
	 * 동작이 이루어진다는 가정하에 작성 된 메소드 <br>
	 * <br>
	 * 특정 경로(src)에 있는 script를 원하는 skin_no와 display_location에 위치시키기 위한 메소드. <br>
	 * HttpMethod : POST<br>
	 * 
	 * @param {@link Scripttags} scripttags
	 * @since 2018-07-03
	 * @author qyuee
	 */
	@Override
	public Scripttags create(Scripttags scripttags) {
		/*
		 * MultiValueMap 객체를 넘기니 AllEncompassingFormHttpMessageConverter를 사용하여 requestBody에 writing 함.
		 * String을 넘기면 StringHttpMessageConverter을 사용함.
		 * headers.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		 */
		
		// postForObject - 결과를 객체 형태로 받겠다는 것.
		// 두번째 파라미터인 request에 String을 입력하면 StringHttpMessageConverter가 반응.
		// Map 형태를 전달하면 AllEncompassingFormHttpMessageConverter가 반응.
		// POJO 객체를 전달하면 MappingJackson2HttpMessageConverter가 반응하여 JSON형태로 변환함.
		/* 
		 * 400에러가 뜨던 이유 : header에 Content-type : application/json이 없었음.
		 * getList(), get(), count() 같은 경우 requestbody에 파라미터를 전송하지 않고 querySring에 전송하기에
		 * Content-type이 따로 명시되어 있지 않더라고 영향이 없었음.
		 * 하지만, POST같은 경우 header에 전달할 값을 json 형태로 전달하기에 Content-type : application/json이 필요했음.
		 * usingApiRestTemplate가 동작하기 전에 {@link Cafe24ApiHeaderBearerOAuth2RequestInterceptor}라는 인터셉터가 있음.
		 * 이 인터셉터에서 header에 accesstoken 값을 추가하고 content-type도 추가함.
		 */
		apiUrl = URIBuilder.buildApiUri(SCRIPTTAGS_PATH);
		HttpEntity<String> entity = prettyRequestBodyConverter(scripttags);
		return usingApiRestTemplate.postForObject(apiUrl, entity, Scripttags.class);
	}

	/**
	 * script_no를 통해서 특정 script에 대한 정보를 조회하는 메소드<br>
	 * <br>
	 * [참고] https://developer.cafe24.com/docs/api/admin/#get-a-scripttag<br>
	 * <br>
	 * @since 2018-07-03
	 * @author qyuee
	 */
	@Override
	public Scripttags get(String scriptNo) {
		apiUrl = URIBuilder.buildApiUri(SCRIPTTAGS_PATH, scriptNo);
		String jsonStr = usingApiRestTemplate.getForObject(apiUrl, String.class);
		Scripttags scripttags = Cafe24ApiJsonParser.parser(jsonStr, Scripttags.class);
		return scripttags;
	}

	/**
	 * 현재 등록된 script list를 조회하는 메소드.<br>
	 * <br>
	 * [참고] https://developer.cafe24.com/docs/api/admin/#list-all-scripttags<br>
	 * <br>
	 * @since 2018-07-03
	 * @author qyuee
	 * @throws IOException 
	 */
	@Override
	public List<Scripttags> getList() throws IOException {
		apiUrl = URIBuilder.buildApiUri(SCRIPTTAGS_PATH);
		String jsonStr = usingApiRestTemplate.getForObject(apiUrl, String.class);
		Scripttags scripttags = Cafe24ApiJsonParser.parser(jsonStr, Scripttags.class);
		return scripttags.getList();
	}
	
	/**
	 * 만능 parser 제작 base
	 * @deprecated
	 * @param json
	 * @return 
	 * @throws IOException
	 */
	public List<Scripttags> parse(String json) throws IOException  {
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		JsonNode rootNode = mapper.readTree(json);  
		Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
		while (fieldsIterator.hasNext()) {
			Map.Entry<String,JsonNode> field = fieldsIterator.next();
			System.out.println("Key: " + field.getKey() + "\tValue:" + field.getValue());
		}
		
		return null;
	}

	/**
	 * @deprecated
	 * 이미 등록되어 있는 script정보를 변경하는 메소드<br>
	 * [변경 가능 한 변수]<br>
	 * 1. src - 기본적인 uri 형식이여야 함. ex) https://xxx.com<br>
	 * 2. display_location - "MAIN", "PRODUCT_LIST"...<br>
	 * 3. skin_no - "1", "2" ...<br>
	 * @param scriptNo, {@link Scripttags}
	 * @return {@link Scripttags}
	 * @since 2018-07-05
	 * @author qyuee
	 */
	@Override
	public Scripttags updateDeprecated(String scriptNo, Scripttags scripttags) {
		apiUrl = URIBuilder.buildApiUri(SCRIPTTAGS_PATH, scriptNo);
		HttpEntity<String> entity = prettyRequestBodyConverter(scripttags);
		ResponseEntity<Scripttags> response = usingApiRestTemplate.exchange(apiUrl, HttpMethod.PUT, entity, Scripttags.class);
		return response.getBody();
	}
	
	/**
	 * 이미 등록되어 있는 script정보를 변경하는 메소드<br>
	 * [변경 가능 한 변수]<br>
	 * 1. src - 기본적인 uri 형식이여야 함. ex) https://xxx.com<br>
	 * 2. display_location - "MAIN", "PRODUCT_LIST"...<br>
	 * 3. skin_no - "1", "2" ...<br>
	 * 
	 * 상태 코드가 200이면 true를 리턴한다.<br>
	 * 
	 * scripttags 객체에는 변경 사항에 대한 값이 들어있음. (src, display_location, skin_no)<br>
	 * 
	 * @param String scriptNo
	 * @since 2018-07-06
	 * @return boolean
	 */
	@Override
	public boolean update(String scriptNo, Scripttags scripttags) {
		apiUrl = URIBuilder.buildApiUri(SCRIPTTAGS_PATH, scriptNo);
		HttpEntity<String> entity = prettyRequestBodyConverter(scripttags);
		ResponseEntity<String> response = usingApiRestTemplate.exchange(apiUrl, HttpMethod.PUT, entity, String.class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * @deprecated
	 * 등록된 script를 삭제하는 메소드. <br>
	 * <br>
	 * @param scriptNo
	 * @return {@link Scripttags}
	 * @since 2019-07-03
	 * @author qyuee
	 */
	@Override 
	public Scripttags deleteDeprecated(String scriptNo) {
		apiUrl = URIBuilder.buildApiUri(SCRIPTTAGS_PATH, scriptNo);
		LinkedMultiValueMap<String, String> deleteRequest = new LinkedMultiValueMap<String, String>();
		deleteRequest.set("method", "delete");
		// 반환 값이 필요하면 exchange() 사용
		// 반환 값이 필요 없으면 delete() 사용
		ResponseEntity<Scripttags> delResult =usingApiRestTemplate.exchange(apiUrl, HttpMethod.DELETE, null, Scripttags.class);
		Scripttags result = delResult.getBody();
		return result;
	}
	
	/**
	 * scriptNo에 맞는 scripttag를 삭제하는 메소드 <br>
	 * 상태 코드가 200이면 true를 리턴한다.<br>
	 * @param String scriptNo
	 * @since 2018-07-06
	 * @return boolean
	 */
	@Override 
	public boolean delete(String scriptNo) {
		apiUrl = URIBuilder.buildApiUri(SCRIPTTAGS_PATH, scriptNo);
		LinkedMultiValueMap<String, String> deleteRequest = new LinkedMultiValueMap<String, String>();
		deleteRequest.set("method", "delete");
		// 반환 값이 필요하면 exchange() 사용
		// 반환 값이 필요 없으면 delete() 사용
		ResponseEntity<String> response = usingApiRestTemplate.exchange(apiUrl, HttpMethod.DELETE, null, String.class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			return true;
		}
		
		return false;
	}

	/**
	 * 현재 등록된 script의 갯수를 리턴하는 메소드<br>
	 * <br>
	 * 
	 * [참고] https://developer.cafe24.com/docs/api/admin/#count-all-scripttags <br>
	 * 
	 * curl -X GET \ <br>
	 * 'https://{mallid}.cafe24api.com/api/v2/admin/scripttags/count' \ <br>
	 * -H 'Authorization: Bearer {access_token}' \ <br>
	 * -H 'Content-Type: application/json' <br>
	 * @return int - 개수
	 * @since 2018-07-03
	 * @author qyuee
	 */
	@Override
	public int count() {
		apiUrl = URIBuilder.buildApiUri(SCRIPTTAGS_PATH, "count");
		String jsonStr = usingApiRestTemplate.getForObject(apiUrl, String.class);
		Scripttags scripttags = Cafe24ApiJsonParser.parser(jsonStr, Scripttags.class);
		return scripttags.getCount();
	}
	
	/**
	 * Scripttags객체를 POST, PUT에서 requestBody에 JSON 형태로 write하기 위한 변환 메소드.<br>
	 * Scripttags를 입력하면 {"request" : .... } 와 같은 형태로 만들어줌.<br>
	 * 
	 * @param scripttags
	 * @return HttpEntity<String>
	 * @author qyuee
	 * @since 2018-07-05
	 */
	private HttpEntity<String> prettyRequestBodyConverter(Scripttags scripttags) {
		// 요청 body에서 {requset : {...}} 형태를 만들기 위해
		Map<String, Object> target = new HashMap<>();
		target.put("request", scripttags);
		String body = null;
		try {
			body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpEntity<String> entity = new HttpEntity<>(body);
		return entity;
	}
}
