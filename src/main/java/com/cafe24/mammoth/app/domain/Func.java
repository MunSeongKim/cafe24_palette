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
	private String filepath;

	@Column(nullable = false, length = 255)
	private String imgpath;
	
	@Column(nullable = true, length = 255)
	private String desciption;

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
		return "Func [funcId=" + funcId + ", name=" + name + ", nameEng=" + nameEng + ", filepath=" + filepath
				+ ", imgpath=" + imgpath + ", desciption=" + desciption + ", updatedDate=" + updatedDate
				+ ", createdDate=" + createdDate + "]";
	}

}
