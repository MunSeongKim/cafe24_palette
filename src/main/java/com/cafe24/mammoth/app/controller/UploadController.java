package com.cafe24.mammoth.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 기능, 테마 업로드 컨트롤러<br>
 * 
 * @since 2018-07-27
 * @author MoonStar
 *
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

	@GetMapping(value = { "/", "" })
	public String index() {
		return "upload/index";
	}

}
