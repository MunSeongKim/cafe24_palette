package com.cafe24.mammoth.oauth2.api.impl.json;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.security.oauth2.common.OAuth2AccessTokenJackson2Deserializer;

import com.cafe24.mammoth.oauth2.api.Scripttags;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * scriptTagsTemplate의 메소드에 의해 발생된 응답의 Response Body 값을 POJO형태로 매핑 해준다.<br>
 * JsonObject내에 Array가 포함된 복합 형태 이므로 따로 Deserializer를 구성함.<br>
 * 
 * JSON String -> POJO<br>
 * 
 * @see JsonDeserializer
 * @see OAuth2AccessTokenJackson2Deserializer
 * @since 2018-07-03
 * @author qyuee
 */
public class ScriptTagsJsonDeserializer extends JsonDeserializer<Scripttags>{

	@Override
	public Scripttags deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		System.out.println("============[ScriptTagsJsonDeserializer is running!!]============");
		List<Scripttags> list = new LinkedList<>();
		Scripttags scripttags = new Scripttags();
		
		p.nextToken();
		
		System.out.println("p.getCurrentName() : "+p.getCurrentName()+", value : "+p.getText());
		if(p.getCurrentName().equals("scripttags")) {
			p.nextToken();
			System.out.println(p.getText());
			
			// getList() 일 때.
			if(p.getCurrentToken() == JsonToken.START_ARRAY) {
				p.nextToken();
				System.out.println("START_ARRAY!!!!");
			}
		}
		
		while(true) {
			p.nextToken();
			
			if(p.getCurrentToken() == JsonToken.START_OBJECT) {
				p.nextToken();
				System.out.println("START_SUBOBJECT!!!!");
			}else if(p.getCurrentToken() == JsonToken.END_OBJECT) {
				list.add(scripttags);
				System.out.println("END_SUBOBJECT!!!!");
				
				p.nextToken();
				
				if(p.getCurrentToken() == JsonToken.END_OBJECT) {
					break;
				}
				
				if(p.getCurrentToken() == JsonToken.END_ARRAY) {
					scripttags.setList(list);
					break;
				}
				
				p.nextToken();
				scripttags = new Scripttags();
			}
			
			String name = p.getCurrentName();
			p.nextToken();
			System.out.println("name : "+name+", value : "+p.getText()+", p.getCurrentToken() : "+p.getCurrentToken());
			
			// if) use count method of Scripttags.
			// only retuen count value
			if(name.equals("count")) {
				scripttags.setCount(p.getValueAsInt());
				break;
			}
			
			if(name.equals("shop_no")) {
				scripttags.setShopNo(p.getText());
			}else if(name.equals("script_no")) {
				scripttags.setScriptNo(p.getText());
			}else if(name.equals("client_id")) {
				scripttags.setClientId(p.getText());
			}else if(name.equals("src")) {
				scripttags.setSrc(p.getText());
			}else if(name.equals("display_location")) {
				scripttags.setDisplayLocation(parseMultivalue(p));
			}else if(name.equals("skin_no")) {
				scripttags.setSkinNo(parseMultivalue(p));
			}else if(name.equals("created_date")) {
				scripttags.setCreatedDate(p.getText());
			}else if(name.equals("updated_date")) {
				scripttags.setUpdatedDate(p.getText());
			}
		}
		System.out.println("============[ScriptTagsJsonDeserializer is End!!]============");
		return scripttags;
	}
	
	/**
	 * Json String 값이 array 형태 일 때. array의 시작부터 끝 사이의 값을 <br>
	 * Set<String>에 담아 리턴한다. <br>
	 * <br>
	 * 
	 * ex) skin_no : ["3", "5"] --> TreeSet<String>에 넣어준다. <br>
	 * 
	 * @see OAuth2AccessTokenJackson2Deserializer
	 * @param JsonParser
	 * @return Set<String>
	 * @throws JsonParseException
	 * @throws IOException
	 */
	private Set<String> parseMultivalue(JsonParser p) throws JsonParseException, IOException{
		Set<String> values = null;
		if(p.getCurrentToken() == JsonToken.START_ARRAY) {
			values = new TreeSet<String>();
			while(p.nextToken() != JsonToken.END_ARRAY) {
				values.add(p.getValueAsString());
			}
		}
		return values;
	}
}
