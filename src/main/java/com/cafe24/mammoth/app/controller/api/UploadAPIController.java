package com.cafe24.mammoth.app.controller.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.mammoth.app.domain.Function;
import com.cafe24.mammoth.app.domain.Theme;
import com.cafe24.mammoth.app.service.FunctionService;
import com.cafe24.mammoth.app.service.ThemeService;
import com.cafe24.mammoth.app.support.JSONResult;

@RestController
@RequestMapping("/api/app/upload")
public class UploadAPIController {

	@Autowired
	private FunctionService functionService;
	@Autowired
	private ThemeService themeService;
	
	@GetMapping("/check")
	public JSONResult check(@RequestParam("engName") String engName) {
		Boolean result = functionService.isExist(engName);
		return result ? JSONResult.fail("exist") : JSONResult.success("not exist");
	}
	
	@PostMapping("/function")
	public ResponseEntity<?> upload(@ModelAttribute Function func,
			@RequestParam("file") MultipartFile file)
			throws IOException {

		// 기능 파일 저장 처리
			if( !functionService.saveFile(func, file) ) {
				//return new ResponseEntity<>("Success to save", HttpStatus.OK);
				return new ResponseEntity<>("Fail to save", HttpStatus.BAD_REQUEST);
			}
			
			return new ResponseEntity<>("Success to save", HttpStatus.OK);
			
	}
	
	@PostMapping("/theme")
	public ResponseEntity<?> upload(@ModelAttribute Theme theme,
			@RequestParam("imgFile") MultipartFile imgFile,
			@RequestParam("file") MultipartFile file){
		System.out.println(theme);
		if( !themeService.saveFile(theme, imgFile, file) ) {
			return new ResponseEntity<>("Fail to save", HttpStatus.BAD_REQUEST);
		}
	
		return new ResponseEntity<>("Success to save", HttpStatus.OK);
	}
}
