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

/**
 * 기능, 테마 업로드 API 컨트롤러<br>
 * 같은 {@link FileUploader} 클래스로 2개 타입의 파일 저장 실행<br>
 * 
 * @since 2018-07-27
 * @author MoonStar
 *
 */
@RestController
@RequestMapping("/api/app/upload")
public class UploadAPIController {

	@Autowired
	private FunctionService functionService;
	@Autowired
	private ThemeService themeService;

	/**
	 * 기능 업로드 전 같은 영문명 체크
	 * 
	 * @param engName
	 * @return JSON 결과
	 */
	@GetMapping("/check")
	public JSONResult check(@RequestParam("engName") String engName) {
		Boolean result = functionService.isExist(engName);
		return result ? JSONResult.fail("exist") : JSONResult.success("not exist");
	}

	/**
	 * 기능 업로드 전 처리<br>
	 * HTML 파일업로드를 위한 메소드<br>
	 * 
	 * @param function
	 * @param desktopFile
	 * @param mobileFile
	 * @return {@link ResponseEntity}
	 * @throws IOException
	 */
	@PostMapping("/function/pre")
	public ResponseEntity<?> upload(@ModelAttribute Function function,
			@RequestParam("desktopFile") MultipartFile desktopFile,
			@RequestParam("mobileFile") MultipartFile mobileFile) throws IOException {

		// 기능 파일 저장 처리
		if (!functionService.saveFile(function, desktopFile, mobileFile)) {
			// return new ResponseEntity<>("Success to save", HttpStatus.OK);
			return new ResponseEntity<>("Fail to save", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Success to save", HttpStatus.OK);
	}

	/**
	 * 기능 HTML 파일과 관련된 리소스 파일 업로드 처리
	 * @param function
	 * @param files
	 * @return {@link ResponseEntity}
	 * @throws IOException
	 */
	@PostMapping("/function")
	public ResponseEntity<?> upload(@ModelAttribute Function function, @RequestParam("files") MultipartFile[] files)
			throws IOException {

		// 기능 파일 저장 처리
		if (!functionService.saveFile(function, files)) {
			// return new ResponseEntity<>("Success to save", HttpStatus.OK);
			return new ResponseEntity<>("Fail to save", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Success to save", HttpStatus.OK);
	}

	/**
	 * 테마 CSS 파일 업로드
	 * @param theme
	 * @param imgFile
	 * @param files
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/theme")
	public ResponseEntity<?> upload(@ModelAttribute Theme theme,
			@RequestParam("imgFile") MultipartFile imgFile,
			@RequestParam("files") MultipartFile files) {
		if (!themeService.saveFile(theme, imgFile, files)) {
			return new ResponseEntity<>("Fail to save", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Success to save", HttpStatus.OK);
	}
}
