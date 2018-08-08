package com.cafe24.mammoth.oauth2.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.MultiValueMap;

import com.cafe24.mammoth.oauth2.api.operation.EntityListStructure;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard implements EntityListStructure<Dashboard> {

	@JsonProperty(value="board_list")
	private List<MultiValueMap<String, Object>> boardList;
	
	
	@Override
	public void setList(ArrayList<Dashboard> list) {
	}

	@Override
	public ArrayList<Dashboard> getList() {
		return null;
	}

	@Override
	public String toString() {
		return "Dashboard [boardList=" + boardList + "]";
	}

}
