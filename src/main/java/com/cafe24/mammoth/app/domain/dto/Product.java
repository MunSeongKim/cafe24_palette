package com.cafe24.mammoth.app.domain.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
public class Product {
	private String productNo;
	private String smallImage;
	private List<Map<String, String>> categories;
}
