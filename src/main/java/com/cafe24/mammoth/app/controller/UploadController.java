package com.cafe24.mammoth.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/upload")
public class UploadController {

	@GetMapping(value = { "/", "" })
	public String index() {
		return "upload/index";
	}

	
	/*
	 * @PostMapping(value = "/single_upload") public String
	 * fileUpload(@RequestParam("name") String name,
	 * 
	 * @RequestParam("file") MultipartFile file, RedirectAttributes
	 * redirectAttributes) {
	 * 
	 * System.out.println("/single_upload ======> 들어옴");
	 * System.out.println("file ==> " + file.getOriginalFilename());
	 * System.out.println("fileContentType ==> " + file.getContentType());
	 * 
	 * if (name.contains("/")) { redirectAttributes.addFlashAttribute("message",
	 * "Folder separators not allowed"); return "redirect:/files"; }
	 * 
	 * if (!file.isEmpty()) { try { String exeName =
	 * file.getOriginalFilename().split("\\.")[1];
	 * //System.out.println("exeName ==> " + exeName[1]);
	 * System.out.println("exeName ==> " + exeName);
	 * 
	 * BufferedOutputStream stream = new BufferedOutputStream( new
	 * FileOutputStream(new File(UPLOAD_DIR_PATH + "/" + name + "." + exeName)));
	 * FileCopyUtils.copy(file.getInputStream(), stream); stream.close();
	 * redirectAttributes.addFlashAttribute("message", "You successfully uploaded "
	 * + name + "!"); } catch (Exception e) {
	 * redirectAttributes.addFlashAttribute("message", "You failed to upload " +
	 * name + " => " + e.getMessage()); } } else {
	 * redirectAttributes.addFlashAttribute("message", "You failed to upload " +
	 * name + " because the file was empty"); }
	 * 
	 * //return "redirect:/funcupload/"; return "success!!"; }
	 */

}
