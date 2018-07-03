package com.cafe24.mammoth.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/* 
   {
 	Access_Token=....,
  	expires_at=2018-06-29T12:23:13.216, 
	client_id=PLJ3LsIxIdkkcvwiEjejoH, 
	mall_id=qyuee,
	user_id=qyuee,
	scopes={
		mall.read_category,
		mall.write_category,
		mall.read_store,
		mall.write_store
		}, 
	issued_at=2018-06-29T10:23:13.219,
	refresh_token : ...,
	}

*/
@Entity
@Table(name="auth")
public class Auth{
	
	@Id
	@Column(nullable=false, length=50, name="mall_id")
	private String mallId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="issued_at")
	private Date issuedAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expires_at")
	private Date expiresAt;
	
	@Column(name="access_token")
	private String accessToken;
	
	@Column(name="refresh_token")
	private String refreshToken;
	
	@Column(name="scopes")
	private String scopes;

	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

	public Date getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}

	public Date getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getScopes() {
		return scopes;
	}

	public void setScopes(String scopes) {
		this.scopes = scopes;
	}
	
}
