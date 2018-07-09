package com.cafe24.mammoth.oauth2.api.impl;

import java.net.URI;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cafe24.mammoth.oauth2.api.Themes;
import com.cafe24.mammoth.oauth2.api.ThemesOperations;
import com.cafe24.mammoth.oauth2.api.impl.json.Cafe24ApiJsonParser;
import com.cafe24.mammoth.oauth2.api.support.URIBuilder;

public class ThemesTemplate implements ThemesOperations{

	private static final String THEMES_PATH = "/api/v2/admin/themes";
	private RestTemplate usingApiRestTemplate;
	private URI apiUrl;
	private String baseUrl;
	
	public ThemesTemplate(RestTemplate usingApiRestTemplate, String baseUrl) {
		this.usingApiRestTemplate = usingApiRestTemplate;
		this.baseUrl = baseUrl+THEMES_PATH;
	}
	
	/**
	 * 쇼핑몰의 테마의 목록을 조회.<br>
	 * @since 2018-07-05
	 * @author qyuee
	 */
	@Override
	public List<Themes> getList() {
		MultiValueMap<String, String> addtionalParam = new LinkedMultiValueMap<>();
		addtionalParam.add("type", "pc");
		apiUrl = URIBuilder.buildApiUri(baseUrl, addtionalParam);
		String jsonStr = usingApiRestTemplate.getForObject(apiUrl, String.class);
		Themes themes = Cafe24ApiJsonParser.parser(jsonStr, Themes.class);
		return themes.getList();
	}
	
	/**
	 * skin_no에 따라 특정 테마 상세정보 조회.
	 * @author qyuee
	 * @since 2018-07-06
	 */
	@Override
	public Themes get(String skinNo) {
		apiUrl = URIBuilder.buildApiUri(baseUrl, skinNo);
		String jsonStr = usingApiRestTemplate.getForObject(apiUrl, String.class);
		Themes themes = Cafe24ApiJsonParser.parser(jsonStr, Themes.class);
		return themes;
	}

}
