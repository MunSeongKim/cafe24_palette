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
	public final String UPLOAD_DIR_FUNC_PATH = "/cafe24/tmp/function/";
	
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
	
	public boolean saveFile(Function function, MultipartFile file) {
		String path = fileUploader.makeDirectory(UPLOAD_DIR_FUNC_PATH, function.getNameEng());
		String filePath = fileUploader.restoreFile(path, file);
		
		if(filePath.contains("html")) {
			function.setFilePath(filePath);
			function.setCreatedDate(new Date());
			return functionRepository.save(function) != null ? true : false;
		}
		
		return filePath != null ? true : false;
	}

	public Boolean isExist(String engName) {
		return functionRepository.existsByEngName(engName);
	}
}
