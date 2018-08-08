package com.cafe24.mammoth.oauth2.api.operation;

import java.util.List;

import org.springframework.util.MultiValueMap;

public interface DashboardOperations {
	List<MultiValueMap<String, Object>> getBoardList(MultiValueMap<String, String> params);
}
