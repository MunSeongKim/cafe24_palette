package com.cafe24.mammoth.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Theme;
import com.cafe24.mammoth.app.repository.ThemeRepository;

@Service
public class ThemeService {
	@Autowired
	private ThemeRepository themeRepository;
	
	public boolean saveTheme(Theme theme) {
		return themeRepository.save(theme) != null ? true : false;
	}
	
	public void removeTheme(Long id) {
		themeRepository.deleteById(id);
	}
	
	public List<Theme> getThemeList(){
		return themeRepository.findAll();
	}

}
