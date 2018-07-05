package com.cafe24.mammoth.oauth2.api;

import java.util.List;
import java.util.Set;

import com.cafe24.mammoth.oauth2.api.impl.json.ScriptTagsJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 curl -X POST \
  'https://{mallid}.cafe24api.com/api/v2/admin/scripttags' \
  -H 'Authorization: Bearer {access_token}' \
  -H 'Content-Type: application/json' \
  -d '{
    "shop_no": 1,
    "request": {
        "src": "https:\/\/js-aplenty.com\/bar.js",
        "display_location": [
            "PRODUCT_LIST",
            "PRODUCT_DETAIL"
        ],
        "skin_no": [
            3,
            4
        ]
    }
 }'
 * 
 * "scripttags": [
        {
            "shop_no": 1,
            "script_no": "1509432821494844",
            "client_id": "AMj8UZhBC9zsyTlFGI6PzC",
            "src": "https:\/\/js-aplenty.com\/bar.js",
            "display_location": [
                "BOARD_FREE_LIST"
            ],
            "skin_no": [
                1,
                2
            ],
            "created_date": "2017-10-31T15:53:41+09:00",
            "updated_date": "2017-11-03T18:05:32+09:00"
        },
        {
            "shop_no": 1,
            "script_no": "1509699932016345",
            "client_id": "AMj8UZhBC9zsyTlFGI6PzC",
            "src": "https:\/\/js-aplenty.com\/bar.js",
            "display_location": [
                "PRODUCT_LIST",
                "PRODUCT_DETAIL"
            ],
            "skin_no": null,
            "created_date": "2017-11-03T18:05:32+09:00",
            "updated_date": "2017-11-03T18:05:32+09:00"
        }
    ]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = ScriptTagsJsonDeserializer.class)
public class Scripttags {
	
	private String shopNo;
	
	private String scriptNo;
	
	private String clientId;
	
	private String src;
	
	private Set<String> displayLocation;
	
	private Set<String> skinNo;
	
	private String createdDate;
	
	private String updatedDate;
	
	private int count;
	
	class Request{
		@JsonProperty(value="src")
		private String src;
		
		@JsonProperty(value="display_location")
		private Set<String> displayLocation;
		
		@JsonProperty(value="skin_no")
		private Set<String> skinNo;
		
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
		
		public Request(Scripttags scripttags) {
			//this.ShopNo = scripttags.getShopNo();
			this.src = scripttags.getSrc();
			this.displayLocation = scripttags.getDisplayLocation();
			this.skinNo = scripttags.getSkinNo();
		}
	}
	
	public Request getRequest(Scripttags scripttags) {
		return new Request(scripttags);
	}
	
	/*// when create a Scripttags
	// (String)src, (SET)display_loaction, (SET)skin_no
	private Map<String, Object> request;*/
	
	private List<Scripttags> list;

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

	public List<Scripttags> getList() {
		return list;
	}

	public void setList(List<Scripttags> list) {
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
