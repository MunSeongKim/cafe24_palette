package com.cafe24.mammoth.app.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.mammoth.app.domain.Function;
import com.cafe24.mammoth.app.repository.FunctionRepository;
import com.cafe24.mammoth.app.support.FileUploader;

@Service
@Transactional
public class FunctionService {
	
	public final String UPLOAD_DIR_FUNC_PATH = "/cafe24/tmp/function";
	/*public final String UPLOAD_DIR_FUNC_PATH = "C:/Users/bit/git/mammoth/src/main/resources/static/";*/
	
	@Autowired
	private FileUploader fileUploader;
	
	@Autowired
	private FunctionRepository functionRepository; 
	
	public boolean save(Function function) {
		return functionRepository.save(function) != null ? true : false;
	}
	
	public List<Function> getFuncList(){
		return functionRepository.findAll();
	}
	
	public boolean saveFile(Function function, MultipartFile desktopFile, MultipartFile mobileFile ) {
		String path = fileUploader.makeDirectory(UPLOAD_DIR_FUNC_PATH, function.getNameEng());
		String desktopPath = fileUploader.restoreFile(path, desktopFile);
		String mobilePath = fileUploader.restoreFile(path, mobileFile);

		function.setDesktopPath(desktopPath.replace("/cafe24/tmp", ""));
		function.setMobilePath(mobilePath.replace("/cafe24/tmp", ""));

		function.setCreatedDate(new Date());
		return functionRepository.save(function) != null ? true : false;
	}
	
	public boolean saveFile(Function function, MultipartFile[] files) {
		String path = fileUploader.makeDirectory(UPLOAD_DIR_FUNC_PATH, function.getNameEng());
		for(MultipartFile file : files) {
			String desktopPath = fileUploader.restoreFile(path, file);
			if(desktopPath == null) {
				return false;
			}
		}
		return true;
	}

	public Boolean isExist(String engName) {
		return functionRepository.existsByEngName(engName);
	}
}
