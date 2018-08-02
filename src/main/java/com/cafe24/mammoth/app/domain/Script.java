package com.cafe24.mammoth.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Script {

	@Id
	@Column(name = "panel_id")
	private Long panelId;

	@Column(nullable = false, length = 255)
	private String filepath;

	@Column(nullable = false)
	private Boolean isApply;

	@Column(length = 20)
	private String scripttagsNo;

	@Column(nullable = true, length = 150)
	private String dpLocation;

	@MapsId
	@OneToOne
	@JoinColumn(name = "panel_id")
	private Panel panel;

	@Override
	public String toString() {
		return "Script [panelId=" + panelId + ", filepath=" + filepath + ", isApply=" + isApply + ", scripttagsNo="
				+ scripttagsNo + ", dpLocation=" + dpLocation + "]";
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
		if(panel.getScript() != this) {
			panel.setScript(this);
		}
	}

}
