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

import com.cafe24.mammoth.app.domain.enumerate.Action;
import com.cafe24.mammoth.app.domain.enumerate.PType;
import com.cafe24.mammoth.app.domain.enumerate.Position1;
import com.cafe24.mammoth.app.domain.enumerate.Position2;

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

	@Column(length=6)
	private String width;
	
	@Column(length=6)
	private String height;

	@Enumerated(EnumType.STRING)
	@Column(name = "p_action", nullable = false, columnDefinition = "enum('STATIC', 'TOGGLE')")
	private Action pAction;

	@Column(columnDefinition = "varchar(7) default '#FFFFFF'")
	private String bgColor;

	@Column(columnDefinition = "varchar(7) default '#000000'")
	private String textColor;

	@Enumerated(EnumType.STRING)
	@Column(name = "position1", nullable = false, columnDefinition = "enum('LEFT','BOTTOM','RIGHT')")
	private Position1 position1;

	@Enumerated(EnumType.STRING)
	@Column(name = "position2", nullable = true, columnDefinition = "enum('TOP', 'CENTER', 'BOTTOM')")
	private Position2 position2;

	@Enumerated(EnumType.STRING)
	@Column(name = "p_type", nullable = false, columnDefinition = "enum('STICK', 'ISLAND', 'FULL')")
	private PType pType;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mall_id", insertable = false, updatable = false)
	private Member member;

	@OneToOne(mappedBy = "panel", cascade = CascadeType.REMOVE)
	private Script script;

	@OneToMany(mappedBy = "panel", cascade = CascadeType.REMOVE)
	private List<SelectFunc> selectFuncs = new ArrayList<>();

	public void setMember(Member member) {
		if (this.member != null) {
			this.member.getPanels().remove(this);
		}
		this.member = member;
		if (member != null) {
			member.getPanels().add(this);
		}
	}

	@Override
	public String toString() {
		return "Panel [panelId=" + panelId + ", name=" + name + ", width=" + width + ", height=" + height + ", pAction="
				+ pAction + ", bgColor=" + bgColor + ", textColor=" + textColor + ", position1=" + position1
				+ ", position2=" + position2 + ", pType=" + pType + ", createdDate=" + createdDate + "]";
	}

}
