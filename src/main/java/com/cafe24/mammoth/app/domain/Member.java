package com.cafe24.mammoth.app.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

	@Column(name = "base_domain", nullable = true, length = 200)
	private String baseDomain;
	
	@Column(name = "primary_domain", nullable = true, length = 200)
	private String primaryDomain;
	
	@Column(name = "mall_url", nullable = true, length = 200)
	private String mallUrl;

	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<Panel> panels = new ArrayList<>();

	
	@Override
	public String toString() {
		return "Member [mallId=" + mallId + ", panelUsed=" + panelUsed + ", baseDomain=" + baseDomain
				+ ", primaryDomain=" + primaryDomain + ", mallUrl=" + mallUrl + "]";
	}

}
