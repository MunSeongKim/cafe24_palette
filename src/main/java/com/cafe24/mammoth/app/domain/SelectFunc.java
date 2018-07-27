package com.cafe24.mammoth.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class SelectFunc {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "panel_id")
	private Panel panel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "func_id")
	private Function func;

	@Column(nullable = false)
	private Long funcOrder;

	public void setPanel(Panel panel) {
		if (this.panel != null) {
			this.panel.getSelectFuncs().remove(this);
		}
		this.panel = panel;
		if (panel != null) {
			panel.getSelectFuncs().add(this);
		}
	}

	public void setFunc(Function func) {
		if (this.func != null) {
			this.func.getSelectFuncs().remove(this);
		}
		this.func = func;
		if (func != null) {
			func.getSelectFuncs().add(this);
		}
	}

	@Override
	public String toString() {
		return "SelectFunc [id=" + id + ", funcOrder=" + funcOrder + "]";
	}

}
