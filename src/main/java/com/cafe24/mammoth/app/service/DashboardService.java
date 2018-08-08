package com.cafe24.mammoth.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cafe24.mammoth.oauth2.api.impl.Cafe24Template;
import com.cafe24.mammoth.oauth2.api.impl.DashboardTemplate;

/**
 * Cafe24 Dashboard API에서 필요 정보를 추출하는 서비스<br>
 * 해당 쇼핑몰의 게시판 목록을 구성<br>
 * 
 * @since 2018-08-07
 * @author MoonStar
 *
 */
@Service
public class DashboardService {

	@Autowired
	private Cafe24Template cafe24Template;
	
	private final String fields = "board_list";
	
	@Cacheable(value="dashboard")
	public List<MultiValueMap<String, Object>> getBoardList(){
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("fields", fields);
		
		DashboardTemplate dashboardTemplate = cafe24Template.getOperation(DashboardTemplate.class);
		return dashboardTemplate.getBoardList(params);
	}
}
