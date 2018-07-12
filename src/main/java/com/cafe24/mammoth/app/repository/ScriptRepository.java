package com.cafe24.mammoth.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cafe24.mammoth.app.domain.Script;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
	
	@Modifying
	@Query("update Script s set s.isApply = :state where s.panelId = :id")
	public int updateIsApplyByPanelId(@Param("state") boolean state, @Param("id") Long id);
	
	@Modifying
	@Query("update Script s set s.isApply = false where s.panelId != :id and s.isApply = true")
	public int updateIsApplyByExceptPanelId(@Param("id") Long id);

	@Query("select s from Script s where s.panelId != :id and s.isApply = true")
	public Script findByExceptId(@Param("id") Long id);

	@Query("SELECT s FROM Script s WHERE s.isApply = true")
	public Script findByApplied();
}
