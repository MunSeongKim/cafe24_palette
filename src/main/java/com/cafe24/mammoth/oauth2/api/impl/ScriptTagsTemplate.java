package com.cafe24.mammoth.oauth2.api.impl;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	
	private OAuth2AccessToken oAuth2AccessToken;
	private String accessToken;
	private RestTemplate usingApiRestTemplate;
	private URI apiUrl;
	private ObjectMapper objectMapper;
	
	// accessToken이 담긴 OAuth2ClientContext를 받을 때.
	public ScriptTagsTemplate(OAuth2ClientContext context) {
		this.oAuth2AccessToken = context.getAccessToken();
		this.accessToken = oAuth2AccessToken.getValue();
		initallize();
	}
	
	// accessToken을 String으로 직접 받을 때.
	public ScriptTagsTemplate(String accessToken) {
		this.accessToken = accessToken;
		initallize();
	}
	
	private void initallize() {
		usingApiRestTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
		apiUrl = buildApiUri();
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new Cafe24ApiHeaderBearerOAuth2RequestInterceptor(accessToken));
		usingApiRestTemplate.setInterceptors(interceptors); 
		List<HttpMessageConverter<?>> list = usingApiRestTemplate.getMessageConverters();
		objectMapper = new ObjectMapper();
		
		// 등록된 messageConverter log
		for(HttpMessageConverter<?> messageConverter : list) {
			System.out.println(messageConverter.getClass().getName());
		}
		
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
	 * 만능 parser 제작 base
	 * @deprecated
	 * @param json
	 * @return null
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
			
			JsonNode node = field.getValue();
		}
		
		return null;
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
