package com.cafe24.mammoth.oauth2.api.impl.json;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Cafe24ApiJsonParser {
	
	/**
	 * cafe24 api의 응답 값을 정해진 domain 객체에 mapping 해주는 parser.<br>
	 * 
	 * @param jsonStr
	 * @param returnType
	 * @return T
	 * @since 2018-07-06
	 */
	public static <T extends Object> T parser(String jsonStr, Class<T> returnType) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = null;
		List<T> list = new ArrayList<>();
		T pojo = null;
		JsonParser jp = null;
		JsonToken jt = null;
		String topFieldName = null;
		JsonNode arrayNode = null;

		try {
			rootNode = mapper.readTree(jsonStr); // 전체 json String을 rootNode로 잡는다.
			
			// count에 대한 process
			if(rootNode.has("count")) {
				pojo = returnType.newInstance();
				Method setCount = pojo.getClass().getMethod("setCount", int.class);
				setCount.invoke(pojo, rootNode.get("count").asInt());
				return pojo;
			}
			
			jp = rootNode.traverse();
			jp.nextToken();
			jp.nextToken();
			topFieldName = jp.getCurrentName();
			jp.nextToken();
			jt = jp.getCurrentToken();
			//System.out.println(jp.getCurrentToken());
			jp.nextToken();
			
			// 응답이 빈 list인 경우.
			if(jp.getCurrentToken() == JsonToken.END_ARRAY) {
				pojo = returnType.newInstance();
				Method setList = pojo.getClass().getMethod("setList", ArrayList.class);
				setList.invoke(pojo, list);
				return pojo;
			}
			
			arrayNode = rootNode.get(topFieldName);
			
			// 응답이 list인 경우 (1개 이상, 복합 응답, []으로 구분)
			if (jt == JsonToken.START_ARRAY) {
				// getList() 인 상황.
				Iterator<JsonNode> elements = arrayNode.elements();
				
				while (elements.hasNext()) {
					JsonNode objectString = elements.next();
					// System.out.println(objectString);
					pojo = mapper.convertValue(objectString, returnType);
					//System.out.println("parser : " + pojo);
					list.add(pojo);
				}
				
				Method setList = pojo.getClass().getMethod("setList", ArrayList.class);
				setList.invoke(pojo, list);
				return pojo;
			}
			
			// 응답이 object인 경우. (1개 값, 단일 응답)
			if(jt == JsonToken.START_OBJECT){
				pojo = mapper.convertValue(arrayNode, returnType);
				//System.out.println("pojo.getClass().getTypeName() : " + pojo.getClass().getTypeName());
			}
			
			//System.out.println(rootNode); // 전체 출력.
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pojo;
	}
}
