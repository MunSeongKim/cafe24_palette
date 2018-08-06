package com.cafe24.mammoth.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cafe24.mammoth.app.domain.Function;

@Repository
public interface FunctionRepository extends JpaRepository<Function, Long>{

	@Query("SELECT CASE WHEN COUNT(*) = 1 THEN true ELSE false END FROM Function f WHERE f.nameEng = :engName")
	Boolean existsByEngName(@Param("engName") String engName);

	@Query("SELECT f FROM Function f WHERE f.nameEng = :engName")
	Function findByNameEng(@Param("engName") String nameEng);
}
