package com.cafe24.mammoth.oauth2.api;

import java.util.ArrayList;

import com.cafe24.mammoth.oauth2.api.operation.EntityListStructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard implements EntityListStructure<Dashboard> {

	private String data;
	
	@Override
	public void setList(ArrayList<Dashboard> list) {
		
	}

	@Override
	public ArrayList<Dashboard> getList() {
		return null;
	}

}
