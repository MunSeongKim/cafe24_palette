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

import lombok.Getter;
import lombok.Setter;

/**
 * 기능 도메인 객체
 * @author Deo
 *
 */
@Entity
@Table
@Getter
@Setter
public class Function {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long funcId;

	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, length = 50)
	private String nameEng;

	@Column(nullable = false, length = 255)
	private String desktopPath;
	
	@Column(nullable = false, length = 255)
	private String mobilePath;

	@Column(nullable = true, length = 255)
	private String description;

	@Column(columnDefinition = "boolean DEFAULT 0")
	private Boolean isButton;
	
	@Transient
	private Boolean isAgree;
	
	// 기능을 제작한 제작자 정보 객체 -> 나중에 추가
//	@Transient
//	private Maker maker; 

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdDate;
	
	@OneToMany(mappedBy = "function", cascade = CascadeType.REMOVE)
	private List<SelectFunc> selectFuncs = new ArrayList<>();

	@Override
	public String toString() {
		return "Function [funcId=" + funcId + ", name=" + name + ", nameEng=" + nameEng + ", desktopPath=" + desktopPath
				+ ", mobilePath=" + mobilePath + ", description=" + description + ", isButton=" + isButton
				+ ", createdDate=" + createdDate + "]";
	}

}
