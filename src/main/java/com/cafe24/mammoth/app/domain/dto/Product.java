package com.cafe24.mammoth.app.domain.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
@EqualsAndHashCode
public class Product implements Serializable {
	/**
	 * For Caching
	 */
	private static final long serialVersionUID = -3802502049417564539L;
	private String productNo;
	private String smallImage;
	private List<Map<String, String>> categories;
}
