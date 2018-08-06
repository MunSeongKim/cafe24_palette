package com.cafe24.mammoth.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafe24.mammoth.app.domain.SelectFunc;

@Repository
public interface SelectFuncRepository extends JpaRepository<SelectFunc, Long>{
	@Query("SELECT s.function.funcId, s.function.name, COUNT(s.function) FROM SelectFunc s GROUP BY s.function")
	public List<Object[]> getFunctionCount();
}
