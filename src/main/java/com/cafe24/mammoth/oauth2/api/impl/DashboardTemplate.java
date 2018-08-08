package com.cafe24.mammoth.oauth2.api.impl;

import java.net.URI;
import java.util.List;

import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cafe24.mammoth.oauth2.api.Dashboard;
import com.cafe24.mammoth.oauth2.api.impl.json.Cafe24ApiJsonParser;
import com.cafe24.mammoth.oauth2.api.operation.DashboardOperations;
import com.cafe24.mammoth.oauth2.api.support.URIBuilder;

/**
 * Dashboard API 사용 템플릿<br>
 * Dashboard API 중 boardList만 가져오는 기능 구현
 * 
 * @since 2018-08-07
 * @author MoonStar
 *
 */
public class DashboardTemplate implements DashboardOperations {

	private final String DASHBOARD_URL = "/api/v2/admin/dashboard";
	private URI apiUrl;
	private String baseUrl;
	private RestTemplate restTemplate;

	public DashboardTemplate() {
	}

	public DashboardTemplate(RestTemplate restTemplate, String baseUrl) {
		this.baseUrl = baseUrl + DASHBOARD_URL;
		this.restTemplate = restTemplate;
	}

	@Override
	public List<MultiValueMap<String, Object>> getBoardList(MultiValueMap<String, String> params){
		apiUrl = URIBuilder.buildApiUri(baseUrl, params);
		String jsonResult = restTemplate.getForObject(apiUrl, String.class);
		Dashboard dashboard = Cafe24ApiJsonParser.parser(jsonResult, Dashboard.class);
		return dashboard.getBoardList();
	}
	
}

