package com.cafe24.mammoth.app.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * 테마 도메인 객체
 * @author Deo
 *
 */
@Entity
@Table
@Getter
@Setter
public class Theme {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "theme_id")
	private Long id;

	@Column(nullable = false, length = 100)
	private String title;

	@Column(nullable = false, length = 255)
	private String filePath; // css파일의 위치를 의미

	@Column(nullable = false, length = 255)
	private String titleImgPath;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdDate;
	
	@OneToMany(mappedBy = "theme", fetch=FetchType.LAZY)
	private List<Panel> panels;
	
	@PreRemove
	public void preRemove() {
		if (panels != null) {
			for (Panel p : panels) {
				if (p.getTheme().equals(this)) {
					p.setTheme(null);
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Theme [id=" + id + ", title=" + title + ", filePath=" + filePath + ", titleImgPath=" + titleImgPath
				+ ", createdDate=" + createdDate + "]";
	}

}
