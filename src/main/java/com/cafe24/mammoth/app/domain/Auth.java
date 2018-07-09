package com.cafe24.mammoth.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Auth {

	@Id
	@Column(nullable = false, length = 50)
	private String mallId;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date issuedDate;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date expireDate;

	@Column(nullable = false, length = 100)
	private String accessToken;

	@Column(nullable = false, length = 100)
	private String refreshToken;

	@Column(nullable = false, length = 150)
	private String scope;

	@MapsId
	@OneToOne
	@JoinColumn(name = "mall_id", insertable = false, updatable = false)
	Member member;

	@Override
	public String toString() {
		return "Auth [mallId=" + mallId + ", issuedDate=" + issuedDate + ", expireDate=" + expireDate + ", accessToken="
				+ accessToken + ", refreshToken=" + refreshToken + ", scope=" + scope + "]";
	}

}
