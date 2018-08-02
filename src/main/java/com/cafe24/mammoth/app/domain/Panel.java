package com.cafe24.mammoth.app.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.cafe24.mammoth.app.domain.enumerate.PanelType;
import com.cafe24.mammoth.app.domain.enumerate.Position;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Panel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long panelId;

	@Column(nullable = false, length = 100)
	private String name;

	// 15.625em, 50%, 100%
	@Column(nullable = true, length = 20, columnDefinition = "VARCHAR(20) DEFAULT '15.625em'")
	private String width;

	@Enumerated(EnumType.STRING)
	@Column(name = "position", nullable = false, columnDefinition = "enum('LEFT','BOTTOM','RIGHT')")
	private Position position;

	@Enumerated(EnumType.STRING)
	@Column(name = "panel_type", nullable = true, columnDefinition = "enum('STICK', 'ISLAND', 'FULL')")
	private PanelType panelType;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mall_id")
	private Member member;

	// 요거 수정해야함
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "theme_id", nullable = true)
	private Theme theme;

	@OneToOne(mappedBy = "panel", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Script script;

	@OneToMany(mappedBy = "panel", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<SelectFunc> selectFuncs = new ArrayList<>();

	public void setMember(Member member) {
		this.member = member;
		if (!member.getPanels().contains(this)) {
			System.out.println("타나?");
			member.getPanels().add(this);
			System.out.println("탄다!!");
		}
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
		if (!theme.getPanels().contains(this)) {
			theme.getPanels().add(this);
		}
	}

	@Override
	public String toString() {
		return "Panel [panelId=" + panelId + ", name=" + name + ", width=" + width + ", position=" + position
				+ ", panelType=" + panelType + ", createdDate=" + createdDate + "]";
	}

}
