package com.cafe24.mammoth.oauth2.api;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
"skin_no": 3,
"skin_code": "skin2",
"skin_name": "My Shop Default Theme",
"skin_thumbnail_url": "http:\/\/img.echosting.cafe24.com\/smartAdmin\/img\/design\/img_skin_default.jpg",
"usage_type": "C",
"parent_skin_no": 1,
"seller_id": null,
"seller_skin_code": null,
"design_purchase_no": 0,
"design_product_code": null,
"language_code": "ko_KR",
"published_in": "unpublished",
"created_date": "2017-12-20T17:03:24+09:00",
"updated_date": "2017-12-20T17:03:24+09:00"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonDeserialize(using=CommonsDeserializer.class)
public class Themes implements EntityListStructure<Themes>{
	@JsonProperty(value="skin_no")
	private String skinNo;
	
	@JsonProperty(value="skin_code")
	private String skinCode;
	
	@JsonProperty(value="skin_name")
	private String skinName;
	
	@JsonProperty(value="skin_thumbnail_url")
	private String skinThumbnailUrl;
	
	@JsonProperty(value="usage_type")
	private String usageType;
	
	@JsonProperty(value="parent_skin_no")
	private String parentSkinNo;
	
	@JsonProperty(value="seller_id")
	private String sellerId;
	
	@JsonProperty(value="seller_skin_code")
	private String sellerSkinCode;
	
	@JsonProperty(value="design_purchase_no")
	private String designPurchaseNo;
	
	@JsonProperty(value="design_product_code")
	private String designProductCode;
	
	@JsonProperty(value="language_code")
	private String languageCode;
	
	@JsonProperty(value="published_in")
	private String publishedIn;
	
	@JsonProperty(value="created_date")
	private String createdDate;
	
	@JsonProperty(value="updated_date")
	private String updatedDate;
	
	private ArrayList<Themes> list;
	
	public ArrayList<Themes> getList() {
		return list;
	}
	public void setList(ArrayList<Themes> list) {
		this.list = list;
	}
	public String getSkinNo() {
		return skinNo;
	}
	public void setSkinNo(String skinNo) {
		this.skinNo = skinNo;
	}
	public String getSkinCode() {
		return skinCode;
	}
	public void setSkinCode(String skinCode) {
		this.skinCode = skinCode;
	}
	public String getSkinName() {
		return skinName;
	}
	public void setSkinName(String skinName) {
		this.skinName = skinName;
	}
	public String getSkinThumbnailUrl() {
		return skinThumbnailUrl;
	}
	public void setSkinThumbnailUrl(String skinThumbnailUrl) {
		this.skinThumbnailUrl = skinThumbnailUrl;
	}
	public String getUsageType() {
		return usageType;
	}
	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}
	public String getParentSkinNo() {
		return parentSkinNo;
	}
	public void setParentSkinNo(String parentSkinNo) {
		this.parentSkinNo = parentSkinNo;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerSkinCode() {
		return sellerSkinCode;
	}
	public void setSellerSkinCode(String sellerSkinCode) {
		this.sellerSkinCode = sellerSkinCode;
	}
	public String getDesignPurchaseNo() {
		return designPurchaseNo;
	}
	public void setDesignPurchaseNo(String designPurchaseNo) {
		this.designPurchaseNo = designPurchaseNo;
	}
	public String getDesignProductCode() {
		return designProductCode;
	}
	public void setDesignProductCode(String designProductCode) {
		this.designProductCode = designProductCode;
	}
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	public String getPublishedIn() {
		return publishedIn;
	}
	public void setPublishedIn(String publishedIn) {
		this.publishedIn = publishedIn;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	@Override
	public String toString() {
		return "Themes [skinNo=" + skinNo + ", skinCode=" + skinCode + ", skinName=" + skinName + ", skinThumbnailUrl="
				+ skinThumbnailUrl + ", usageType=" + usageType + ", parentSkinNo=" + parentSkinNo + ", sellerId="
				+ sellerId + ", sellerSkinCode=" + sellerSkinCode + ", designPurchaseNo=" + designPurchaseNo
				+ ", designProductCode=" + designProductCode + ", languageCode=" + languageCode + ", publishedIn="
				+ publishedIn + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
	}
	
}
