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
public class Orders implements EntityListStructure<Orders> {
	@JsonProperty(value="shop_no")
	private String shopNo;
	
	@JsonProperty(value="order_id")
	private String orderId;
	
	@JsonProperty(value="order_date")
	private String orderDate;
	
	@JsonProperty(value="member_id")
	private String memberId;
	
	@JsonProperty(value="buyer_name")
	private String buyerName;
	
	@JsonProperty(value="items")
	private List<Map<String, String>> items;
	
	
	@JsonIgnore
	private int count;
	
	@JsonIgnore
	private ArrayList<Orders> list;
	
	@Override
	public void setList(ArrayList<Orders> list) {
		this.list = list;
	}

	@Override
	public ArrayList<Orders> getList() {
		return this.list;
	}

	
	@Override
	public String toString() {
		return "Orders [shopNo=" + shopNo + ", orderId=" + orderId + ", orderDate=" + orderDate + ", memberId="
				+ memberId + ", buyerName=" + buyerName + ", items=" + items + "]";
	}

}
