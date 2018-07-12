package com.cafe24.mammoth.app.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Member {

	@Id
	@Column(name="mall_id", nullable = false, length = 50)
	private String mallId;

	@Column(nullable = false)
	private Boolean panelUsed;

	@Column(name = "mall_url", nullable = false, length = 200)
	private String mallUrl;

	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<Panel> panels;

	@MapsId
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "mall_id", updatable=true)
	private Auth auth;

	@Override
	public String toString() {
		return "Member [mallId=" + mallId + ", panelUsed=" + panelUsed + ", mallUrl=" + mallUrl + "]";
	}
	
	public void setAuth(Auth auth) {
		this.auth = auth;

		if(this.auth.getMember() != null) {
			this.auth.setMember(null);
		}
		
		this.auth.setMember(this);
	}

}
