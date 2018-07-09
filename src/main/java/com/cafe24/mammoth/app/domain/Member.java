package com.cafe24.mammoth.app.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	@Column(name = "mall_id", nullable = false, length = 50)
	private String mallId;

	@Column(nullable = false)
	private Boolean panelUsed;

	@Column(name = "mall_url", nullable = false, length = 200)
	private String mallUrl;

	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<Panel> panels;

	@OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE)
	private Auth auth;

	@Override
	public String toString() {
		return "Member [mallId=" + mallId + ", panelUsed=" + panelUsed + ", mallUrl=" + mallUrl + "]";
	}

}
