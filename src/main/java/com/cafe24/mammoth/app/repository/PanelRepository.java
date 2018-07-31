package com.cafe24.mammoth.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cafe24.mammoth.app.domain.Panel;
@Repository
public interface PanelRepository extends JpaRepository<Panel, Long> {
	
	@Query("select p from Panel p where p.id= :id")
	public Panel findOne(@Param("id") Long id);

	@Query("SELECT p from Panel p JOIN p.member m JOIN p.script s WHERE m.mallId = :mallId AND s.isApply = true")
	public Panel findByMemberIdAndScriptIsIsApplyTrue(@Param("mallId") String mallId);
	
	@Query("SELECT p FROM Panel p JOIN p.member m WHERE m.mallId = :mallId")
	public List<Panel> findAllByMemberId(@Param("mallId") String mallId);

	@Query("select count(p) from Panel p where p.name= :name")
	public int confirmPanelName(@Param("name") String name);
}
