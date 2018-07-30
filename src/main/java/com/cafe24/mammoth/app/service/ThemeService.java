package com.cafe24.mammoth.app.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.mammoth.app.domain.Theme;
import com.cafe24.mammoth.app.repository.ThemeRepository;
import com.cafe24.mammoth.app.support.FileUploader;

@Service
@Transactional
public class ThemeService {
	
	public final String UPLOAD_DIR_THEME_IMG_PATH = "/cafe24/tmp/theme/img";
	public final String UPLOAD_DIR_THEME_CSS_PATH = "/cafe24/tmp/theme/css";
	
	@Autowired
	private FileUploader fileUploader;
	
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

	public boolean saveFile(Theme theme, MultipartFile imgFile, MultipartFile file) {
		String filePath = fileUploader.restoreFile(UPLOAD_DIR_THEME_CSS_PATH, file);
		String imgFilePath = fileUploader.restoreFile(UPLOAD_DIR_THEME_IMG_PATH, imgFile);
		
		if(filePath.contains("css")) {
			theme.setFilePath(filePath);
		}
		theme.setTitleImgPath(imgFilePath);
		theme.setCreatedDate(new Date());
		return themeRepository.save(theme) != null ? true : false;
	}

}
