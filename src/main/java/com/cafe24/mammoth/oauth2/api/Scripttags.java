package com.cafe24.mammoth.oauth2.api;

import java.util.ArrayList;
import java.util.Set;

import com.cafe24.mammoth.oauth2.api.impl.ScripttagsTemplate;
import com.cafe24.mammoth.oauth2.api.operation.EntityListStructure;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Scripttags Api 객체<br>
 * 
 * @JsonProperty으로 표기된 필드는 {@link ScripttagsTemplate#create(Scripttags)} 메소드 사용시 <br>
 * Cafe24 Api 서버에 전달해야 할 적절한 필드를 구분하기 위함.<br>
 * src, display_location, skin_no를 전달하기 위함.<br>
 * 
 * @author qyuee
 * @since 2018-07-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonDeserialize(using = ScriptTagsJsonDeserializer.class)
public class Scripttags implements EntityListStructure<Scripttags>{
	@JsonProperty(value="shop_no")
	private String shopNo;
	
	@JsonProperty(value="script_no")
	private String scriptNo;
	
	@JsonProperty(value="client_id")
	private String clientId;
	
	@JsonProperty(value="src")
	private String src;
	
	@JsonProperty(value="display_location")
	private Set<String> displayLocation;
	
	@JsonProperty(value="skin_no")
	private Set<String> skinNo;
	
	@JsonProperty(value="created_date")
	private String createdDate;

	@JsonProperty(value="updated_date")
	private String updatedDate;
	
	@JsonIgnore
	private int count;
	
	/*// when create a Scripttags
	// (String)src, (SET)display_loaction, (SET)skin_no
	private Map<String, Object> request;*/
	@JsonIgnore
	private ArrayList<Scripttags> list;

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getScriptNo() {
		return scriptNo;
	}

	public void setScriptNo(String scriptNo) {
		this.scriptNo = scriptNo;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Set<String> getDisplayLocation() {
		return displayLocation;
	}

	public void setDisplayLocation(Set<String> displayLocation) {
		this.displayLocation = displayLocation;
	}

	public Set<String> getSkinNo() {
		return skinNo;
	}

	public void setSkinNo(Set<String> skinNo) {
		this.skinNo = skinNo;
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

	public ArrayList<Scripttags> getList() {
		return list;
	}

	public void setList(ArrayList<Scripttags> list) {
		this.list = list;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Scripttags [shopNo=" + shopNo + ", scriptNo=" + scriptNo + ", clientId=" + clientId + ", src=" + src
				+ ", displayLocation=" + displayLocation + ", skinNo=" + skinNo + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + "]";
	}
	
	
	
}
