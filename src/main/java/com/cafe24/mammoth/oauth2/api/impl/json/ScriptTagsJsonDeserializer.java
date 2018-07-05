package com.cafe24.mammoth.oauth2.api.impl.json;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cafe24.mammoth.oauth2.api.Scripttags;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * "scripttags": [
        {
            "shop_no": 1,
            "script_no": "1509432821494844",
            "client_id": "AMj8UZhBC9zsyTlFGI6PzC",
            "src": "https:\/\/js-aplenty.com\/bar.js",
            "display_location": [
                "BOARD_FREE_LIST"
            ],
            "skin_no": [
                1,
                2
            ],
            "created_date": "2017-10-31T15:53:41+09:00",
            "updated_date": "2017-11-03T18:05:32+09:00"
        },
        {
            "shop_no": 1,
            "script_no": "1509699932016345",
            "client_id": "AMj8UZhBC9zsyTlFGI6PzC",
            "src": "https:\/\/js-aplenty.com\/bar.js",
            "display_location": [
                "PRODUCT_LIST",
                "PRODUCT_DETAIL"
            ],
            "skin_no": null,
            "created_date": "2017-11-03T18:05:32+09:00",
            "updated_date": "2017-11-03T18:05:32+09:00"
        }
    ]
 *
 */
public class ScriptTagsJsonDeserializer extends JsonDeserializer<Scripttags>{

	/**
	 * scriptTagsTemplate의 메소드에 의해 발생된 reponse 응답 값을 POJO형태로 매핑 해준다.
	 * JSON -> POJO
	 * 
	 * @since 2018-07-03
	 * @author qyuee
	 */
	@Override
	public Scripttags deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		String shopNo = null;
		String scriptNo = null;
		String clientId = null;
		String src = null;
		Set<String> displayLocation = null;
		Set<String> skinNo = null;
		String createdDate = null;
		String updatedDate = null;
		
		Integer count = null;
		
		List<Scripttags> list = new LinkedList<>();
		Scripttags scripttags = new Scripttags();
		
		while(p.nextToken() != JsonToken.END_OBJECT) {
			if(p.getCurrentName().equals("scripttags")) {
				p.nextToken();
			}
			
			String name = p.getCurrentName();
			p.nextToken();
			//System.out.println("name : "+name+", value : "+p.getText());
			
			// if) use count method of Scripttags.
			// only retuen count value
			if(name.equals("count")) {
				count = p.getValueAsInt();
				scripttags.setCount(count);
				break;
			}
			
			if(name.equals("shop_no")) {
				shopNo = p.getText();
				scripttags.setShopNo(shopNo);
			}else if(name.equals("script_no")) {
				scriptNo = p.getText();
				scripttags.setScriptNo(scriptNo);
			}else if(name.equals("client_id")) {
				clientId = p.getText();
				scripttags.setClientId(clientId);
			}else if(name.equals("src")) {
				src = p.getText();
				scripttags.setSrc(src);
			}else if(name.equals("display_location")) {
				displayLocation = parseMultivalue(p);
				scripttags.setDisplayLocation(displayLocation);
			}else if(name.equals("skin_no")) {
				skinNo = parseMultivalue(p);
				scripttags.setSkinNo(skinNo);
			}else if(name.equals("created_date")) {
				createdDate = p.getText();
				scripttags.setCreatedDate(createdDate);
			}else if(name.equals("updated_date")) {
				updatedDate = p.getText();
				scripttags.setUpdatedDate(updatedDate);
			}
			
			if(p.getCurrentToken()==JsonToken.END_ARRAY) {
				list.add(scripttags);
			}
		}
		scripttags.setList(list);
		return scripttags;
	}
	
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
