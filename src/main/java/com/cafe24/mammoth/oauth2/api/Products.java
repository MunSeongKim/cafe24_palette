package com.cafe24.mammoth.oauth2.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cafe24.mammoth.oauth2.api.operation.EntityListStructure;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products implements EntityListStructure<Products> {
	@JsonProperty(value="product_no")
	private String productNo;
	
	
	@JsonProperty(value="small_image")
	private String smallImage;
	
	@JsonProperty(value="category")
	private List<Map<String, String>> categories;
	
	@JsonIgnore
	private ArrayList<Products> list;
	
	@Override
	public void setList(ArrayList<Products> list) {
		this.list = list;
	}

	@Override
	public ArrayList<Products> getList() {
		return this.list;
	}

}
