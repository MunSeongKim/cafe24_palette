package com.cafe24.mammoth.app.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mammoth.app.service.DashboardService;
import com.cafe24.mammoth.app.support.JSONResult;

/**
 * 게시판 목록 조회 API 컨트롤러 <br>
 * 
 * @since 2018-08-07
 * @author MoonStar
 *
 */
@RestController
@RequestMapping("/api/cafe24")
@CrossOrigin
public class DashboardAPIController {

	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping(value="/dashboard/boardlist")
	public JSONResult boardList() {
		List<MultiValueMap<String, Object>> boardList = dashboardService.getBoardList();
		
		return JSONResult.success(boardList != null ? boardList : "null");
	}
	
}
