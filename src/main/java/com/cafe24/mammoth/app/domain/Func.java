package com.cafe24.mammoth.app.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Func {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long funcId;

	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, length = 50)
	private String nameEng;

	@Column(nullable = false, length = 255)
	private String filePath;
	
	@Column(nullable = false, length = 255)
	private String previewPath;
	
	@Column(nullable = true, length = 255)
	private String imgPath;

	@Column(nullable = true, length = 255)
	private String desciption;

	@Column(columnDefinition = "boolean DEFAULT 0")
	private Boolean isButton;
	
	@Transient
	private Boolean isAgree;
	
	// 기능을 제작한 제작자 정보 객체 -> 나중에 추가
//	@Transient
//	private Maker maker; 
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdDate;
	
	@OneToMany(mappedBy = "func", cascade = CascadeType.REMOVE)
	private List<SelectFunc> selectFuncs = new ArrayList<>();

	@Override
	public String toString() {
		return "Func [funcId=" + funcId + ", name=" + name + ", nameEng=" + nameEng + ", filePath=" + filePath
				+ ", previewPath=" + previewPath + ", imgPath=" + imgPath + ", desciption=" + desciption + ", isButton="
				+ isButton + ", isAgree=" + isAgree + ", updatedDate=" + updatedDate + ", createdDate=" + createdDate
				+ "]";
	}

}
