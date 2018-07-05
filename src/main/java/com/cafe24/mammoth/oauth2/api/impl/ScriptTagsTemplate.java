package com.cafe24.mammoth.oauth2.api.impl;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cafe24.mammoth.oauth2.api.ScriptTagsOperations;
import com.cafe24.mammoth.oauth2.api.Scripttags;
import com.cafe24.mammoth.oauth2.api.support.Cafe24ApiHeaderBearerOAuth2RequestInterceptor;
import com.cafe24.mammoth.oauth2.api.support.URIBuilder;
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
	
	private OAuth2ClientContext context;
	private OAuth2AccessToken oAuth2AccessToken;
	private String accessToken;
	private RestTemplate usingApiRestTemplate;
	private URI apiUrl;
	private ObjectMapper objectMapper;
	
	public ScriptTagsTemplate(OAuth2ClientContext context) {
		this.context = context;
		this.oAuth2AccessToken = context.getAccessToken();
		this.accessToken = oAuth2AccessToken.getValue();
		initallize();
	}
	
	public ScriptTagsTemplate(String accessToken) {
		this.accessToken = accessToken;
		initallize();
	}
	
	private void initallize() {
		
		//ClientHttpRequestFactory httpRequestFactory =  new HttpComponentsClientHttpRequestFactory();
		
		// [참고] - http://vnthf.logdown.com/posts/2016/03/13/633218
		// HttpRequest는 java.net.HttpURLConenction에서 제공해주는 SimpleClientHttpRequest를 쓰고 있다.
		usingApiRestTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
		apiUrl = buildApiUri();
		
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new Cafe24ApiHeaderBearerOAuth2RequestInterceptor(accessToken));
		usingApiRestTemplate.setInterceptors(interceptors); 
		
		List<HttpMessageConverter<?>> list = usingApiRestTemplate.getMessageConverters();
		
		objectMapper = new ObjectMapper();
		
		/*  usingApiRestTemplate = new RestTemplate(); 일 때.
			org.springframework.http.converter.ByteArrayHttpMessageConverter
			org.springframework.http.converter.StringHttpMessageConverter
			org.springframework.http.converter.ResourceHttpMessageConverter
			org.springframework.http.converter.xml.SourceHttpMessageConverter
			org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter
			org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter
			org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
			
			usingApiRestTemplate = new RestTemplate(new SimpleClientHttpRequestFactory()); 일 때.
			org.springframework.http.converter.ByteArrayHttpMessageConverter
			org.springframework.http.converter.StringHttpMessageConverter
			org.springframework.http.converter.ResourceHttpMessageConverter
			org.springframework.http.converter.xml.SourceHttpMessageConverter
			org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter
			org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter
			org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
			
			별 차이 없음.
		 */
		// 등록된 messageConverter log
		for(HttpMessageConverter<?> messageConverter : list) {
			System.out.println(messageConverter.getClass().getName());
		}
		
		// objectMapper에 모듈을 추가하여 원하는 형태로 json을 작성할 수 있음.
		// 모듈을 선언함과 동시에 정의하는 구문.
		/*SimpleModule simpleModule = new SimpleModule("Qyuee's Json Module", new Version(1, 0, 0, null, null, null));
		simpleModule.addSerializer(Map.class, new JsonSerializer<Map>() {
			@SuppressWarnings("unchecked")
			@Override
			public void serialize(Map map, JsonGenerator jp, SerializerProvider serializers) throws IOException {
				 jp.writeStartObject();
				 
			     for (Object key : map.keySet()) {
			    	 
			    	 if(map.get(key) instanceof Map<?, ?>) {
			    		 Map<String, Object> innerMap = (HashMap<String, Object>) map.get(key);
			    		 for(Object innerkey : innerMap.keySet()) {
				    		 if(innerMap.get(innerkey) instanceof Set<?>) {
				    			 jp.writeFieldName(((String)innerkey).replaceAll(" ", ""));
				    			 jp.writeStartArray();  // array 시작 '['
				    			 
				    			 Set<?> values = (Set<?>)innerMap.get(innerkey);
				    			 
				    			 Iterator<Object> iterator = (Iterator<Object>) values.iterator();
				    			 while(iterator.hasNext()) {
				    				 Object target = iterator.next();
				    				 if(target instanceof Integer) {
				    					 jp.writeString(String.valueOf((Integer)target));
				    				 }else {
				    					 jp.writeString((String)target);
				    				 }
				    			 }
				    			 jp.writeEndArray();    // array 종료 ']'
				    		 }else {
				    			 jp.writeStringField(((String)innerkey).replaceAll(" ", ""), innerMap.get(innerkey).toString());
				    		 }
			    		 }
			    	 }
			     }
			     
			     jp.writeEndObject();
			}
			
		});
		
		objectMapper.registerModule(new AfterburnerModule());
		objectMapper.registerModule(simpleModule);*/
	}
	
	/**
	 * 사용자로 부터 view단에서 src, display_location, skin_no를 받아 @Requestbody로 POJO(Scripttags)에 매핑한 뒤
	 * 동작이 이루어진다는 가정하에 작성 된 메소드 <br>
	 * <br>
	 * 특정 경로(src)에 있는 script를 원하는 skin_no와 display_location에 위치시키기 위한 메소드. <br>
	 * HttpMethod : POST<br>
	 * 
	 * <br>
	 * [curl 요청 예]<br>
	 * curl -X POST \<br>
	 *  'https://{mallid}.cafe24api.com/api/v2/admin/scripttags' \<br>
	 *  -H 'Authorization: Bearer {access_token}' \<br>
	 *  -H 'Content-Type: application/json' \<br>
	 *  -d '{<br>
	 *    "shop_no": 1,<br>
	 *    "request": {<br>
	 *        "src": "https:\/\/js-aplenty.com\/bar.js",<br>
	 *        "display_location": [<br>
	 *            "PRODUCT_LIST",<br>
	 *            "PRODUCT_DETAIL"<br>
	 *        ],<br>
	 *        "skin_no": [<br>
	 *            3,<br>
	 *            4<br>
	 *        ]<br>
	 *    }<br>
	 * }'<br>
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
		 * 
		 * headers.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		 * 
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
		HttpEntity<String> entity = prettyRequestBodyConverter(scripttags);
		apiUrl = buildApiUri();
		return usingApiRestTemplate.postForObject(apiUrl, entity, Scripttags.class);
	}

	/**
	 * script_no를 통해서 특정 script에 대한 정보를 조회하는 메소드<br>
	 * <br>
	 * [참고] https://developer.cafe24.com/docs/api/admin/#get-a-scripttag<br>
	 * <br>
	 * [curl 요청 예]<br>
	 * curl -X GET \<br>
	 * 'https://{mallid}.cafe24api.com/api/v2/admin/scripttags/1509699932016345' \<br>
	 * -H 'Authorization: Bearer {access_token}' \<br>
	 * -H 'Content-Type: application/json'<br>
	 * @since 2018-07-03
	 * @author qyuee
	 */
	@Override
	public Scripttags get(String scriptNo) {
		apiUrl = buildApiUri(scriptNo);
		return usingApiRestTemplate.getForObject(apiUrl, Scripttags.class);
	}

	/**
	 * 현재 등록된 script list를 조회하는 메소드.<br>
	 * <br>
	 * [참고] https://developer.cafe24.com/docs/api/admin/#list-all-scripttags<br>
	 * <br>
	 * [curl 요청 예]<br>
	 * curl -X GET \<br>
	 * 'https://{mallid}.cafe24api.com/api/v2/admin/scripttags' \<br>
	 * -H 'Authorization: Bearer {access_token}' \<br>
	 * -H 'Content-Type: application/json'<br>
	 * @since 2018-07-03
	 * @author qyuee
	 */
	@Override
	public List<Scripttags> getList() {
		Scripttags scripttags = usingApiRestTemplate.getForObject(apiUrl, Scripttags.class);
		return scripttags.getList();
	}

	/**
	 * 이미 등록되어 있는 script정보를 변경하는 메소드<br>
	 * [변경 가능 한 변수]<br>
	 * 1. src - 기본적인 uri 형식이여야 함. ex) https://xxx.com<br>
	 * 2. display_location - "MAIN", "PRODUCT_LIST"...<br>
	 * 3. skin_no - "1", "2" ...<br>
	 * 
	 * curl -X PUT \<br>
	 * 'https://{mallid}.cafe24api.com/api/v2/admin/scripttags/1509699932016345' \<br>
	 * -H 'Authorization: Bearer {access_token}' \<br>
	 * -H 'Content-Type: application/json' \<br>
	 * -d '{<br>
	 *   "shop_no": 1,<br>
	 *   "request": {<br>
	 *       "display_location": [<br>
	 *           "PRODUCT_LIST",<br>
	 *           "PRODUCT_DETAIL"<br>
	 *       ],<br>
	 *       "skin_no": [<br>
	 *           3,<br>
	 *           4<br>
	 *       ]<br>
	 *   }<br>
	 * }'<br>
	 * @param scriptNo, {@link Scripttags}
	 * @return {@link Scripttags}
	 * @since 2018-07-05
	 * @author qyuee
	 */
	@Override
	public Scripttags update(String scriptNo, Scripttags scripttags) {
		apiUrl = buildApiUri(scriptNo);
		HttpEntity<String> entity = prettyRequestBodyConverter(scripttags);
		ResponseEntity<Scripttags> response = usingApiRestTemplate.exchange(apiUrl, HttpMethod.PUT, entity, Scripttags.class);
		return response.getBody();
	}
	
	/**
	 * 등록된 script를 삭제하는 메소드. <br>
	 * <br>
	 * [curl 요청 예]<br>
	 * curl -X DELETE \<br>
	 * 'https://{mallid}.cafe24api.com/api/v2/admin/scripttags/1509699932016345' \<br>
	 * -H 'Authorization: Bearer {access_token}' \<br>
	 * -H 'Content-Type: application/json'<br>
	 * @param scriptNo
	 * @return {@link Scripttags}
	 * @since 2019-07-03
	 * @author qyuee
	 */
	@Override 
	public Scripttags delete(String scriptNo) {
		
		apiUrl = buildApiUri(scriptNo);
		
		LinkedMultiValueMap<String, String> deleteRequest = new LinkedMultiValueMap<String, String>();
		deleteRequest.set("method", "delete");
		// 반환 값이 필요하면 exchange() 사용
		// 반환 값이 필요 없으면 delete() 사용
		ResponseEntity<Scripttags> delResult =usingApiRestTemplate.exchange(apiUrl, HttpMethod.DELETE, null, Scripttags.class);
		
		Scripttags result = delResult.getBody();
		
		return result;
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
		Scripttags scripttags = usingApiRestTemplate.getForObject(apiUrl, Scripttags.class);
		return scripttags.getCount();
	}
	
	/**
	 * 파라미터가 요청 주소에 추가 될 때.
	 * @param path
	 * @param parameters
	 * @return {@link URI} 
	 * @author qyuee
	 * @since 2018-07-03
	 */
	@SuppressWarnings("unused")
	private URI buildApiUri(MultiValueMap<String, String> parameters) {
		return URIBuilder.fromUri("https://qyuee.cafe24.com" +SCRIPTTAGS_PATH).queryParams(parameters).build();
	}
	
	/**
	 * GET, DELETE, UPDATE Method 사용 시, Uri에 query String이 아닌 pathVariable 형태로 <br>
	 * 값을 전달 할 때 사용하는 메소드. <br>
	 * 
	 * [결과 uri]<br>
	 * ex) buildApiUri(str1, str2, str3)<br>
	 * "https://ex1.cafe24.com/api/v2/admin/scripttags/value1/value2/value3" 의 형태.<br>
	 * 
	 * @param uriValues
	 * @return {@link URI}
	 * @author qyuee
	 * @since 2018-07-05
	 */
	private URI buildApiUri(String...uriValues) {
		List<String> uriValueList = new LinkedList<>();
		for(int i=0; i<uriValues.length; i++) {
			uriValueList.add("/"+(String)uriValues[i]);
		}
		String addtionalUriValue = String.join("", uriValueList);
		return URIBuilder.fromUri("https://qyuee.cafe24.com"+SCRIPTTAGS_PATH+addtionalUriValue).build();
	}
	
	/**
	 * 요청 주소에 파라미터가 포함되지 않을 때.
	 * @param path
	 * @return {@link URI}
	 * @author qyuee
	 * @since 2018-07-03
	 */
	private URI buildApiUri() {
		return URIBuilder.fromUri("https://qyuee.cafe24.com" +SCRIPTTAGS_PATH).build();
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
		target.put("request", new Scripttags().getRequest(scripttags));
		
		String body = null;
		try {
			body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(target);
			//System.out.println(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HttpEntity<String> entity = new HttpEntity<>(body);
		//System.out.println(entity.getBody());
		
		return entity;
	}
	
}
