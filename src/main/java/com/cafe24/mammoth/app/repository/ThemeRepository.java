package com.cafe24.mammoth.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafe24.mammoth.app.domain.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

}
