package com.cafe24.mammoth.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Theme {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Column(nullable = false, length = 255)
	private String cssFilePath; // css파일의 위치를 의미
	
	@Column(nullable = false, length = 255)
	private String titleImgPath;
	
	@Column(nullable = false, length = 255)
	private String previewImgPath;
	
	@Column(nullable = true, length = 255)
	private String description;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdDate;

	@Override
	public String toString() {
		return "Theme [id=" + id + ", title=" + title + ", cssFilePath=" + cssFilePath + ", titleImgPath="
				+ titleImgPath + ", previewImgPath=" + previewImgPath + ", description=" + description
				+ ", updatedDate=" + updatedDate + ", createdDate=" + createdDate + "]";
	}
	
}
