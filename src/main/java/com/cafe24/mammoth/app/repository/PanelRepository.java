package com.cafe24.mammoth.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cafe24.mammoth.app.domain.Panel;

public interface PanelRepository extends JpaRepository<Panel, Long> {
	
	@Query("select p from Panel p where p.id= :id")
	public Panel findOne(@Param("id") Long id);
	
}
