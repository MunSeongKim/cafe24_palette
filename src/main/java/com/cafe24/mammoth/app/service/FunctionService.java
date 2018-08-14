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

/**
 * 기능 업로드를 처리하는 서비스
 * @author MoonStar
 *
 */
@Service
@Transactional
public class FunctionService {
	
	public final String UPLOAD_DIR_FUNC_PATH = "/cafe24/tmp/function";
	
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
	
	/**
	 * HTML 파일을 저장하는 메소드
	 * @param function
	 * @param desktopFile
	 * @param mobileFile
	 * @return 저장 성공여부
	 */
	public boolean saveFile(Function function, MultipartFile desktopFile, MultipartFile mobileFile ) {
		String path = fileUploader.makeDirectory(UPLOAD_DIR_FUNC_PATH, function.getNameEng());
		String desktopPath = fileUploader.restoreFile(path, desktopFile);
		String mobilePath = fileUploader.restoreFile(path, mobileFile);

		Function savedFunction = functionRepository.findByNameEng(function.getNameEng());
		
		if(savedFunction != null) {
			savedFunction.setDesktopPath(desktopPath.replace("/cafe24/tmp", ""));
			savedFunction.setMobilePath(mobilePath.replace("/cafe24/tmp", ""));
			savedFunction.setCreatedDate(new Date());
			savedFunction.setDescription(function.getDescription());
			savedFunction.setName(function.getName());
			return true;
		}
		
		function.setDesktopPath(desktopPath.replace("/cafe24/tmp", ""));
		function.setMobilePath(mobilePath.replace("/cafe24/tmp", ""));
		function.setCreatedDate(new Date());
		return functionRepository.save(function) != null ? true : false;
	}
	
	/**
	 * HTML 파일과 관련된 리소스 파일을 저장하는 메소드
	 * @param function
	 * @param files
	 * @return 저장 성공 여부
	 */
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

	/**
	 * 기능 영문명 중복 체크 메소드
	 * @param engName
	 * @return 존재여부
	 */
	public Boolean isExist(String engName) {
		return functionRepository.existsByEngName(engName);
	}
}
