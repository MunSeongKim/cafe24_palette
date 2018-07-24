package com.cafe24.mammoth.app.domain.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Order {
	private String orderId;
	private String orderDate;
	// private List<Map<String, Object>> items;
	private List<Item> items = new ArrayList<Item>();

	public Item newItemInstance() {
		return new Item();
	}

	public Item newItemInstance(String itemNo, String productNo, String productName, String productPrice,
			String optionValue, String quantity, String additionalDiscountPrice, Product product) {
		return new Item(itemNo, productNo, productName, productPrice, optionValue, quantity, additionalDiscountPrice,
				product);
	}

	public void addItem(Item item) {
		this.items.add(item);
	}

	public void addItem(String itemNo, String productNo, String productName, String productPrice, String optionValue,
			String quantity, String additionalDiscountPrice, Product product) {
		this.items.add(new Item(itemNo, productNo, productName, productPrice, optionValue, quantity,
				additionalDiscountPrice, product));
	}

	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	private class Item {
		private String itemNo;
		private String productNo;
		private String productName;
		private String productPrice;
		private String optionValue;
		private String quantity;
		private String additionalDiscountPrice;
		private Product product;

		// public void addProduct(Product product) {
		// this.products.add(product);
		// }
	}
}
