package com.cafe24.mammoth.app.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafe24.mammoth.app.support.JSONResult;

@Controller
@RequestMapping("/funcupload")
// SessionAttributes는 고려해봐야할 사항
@SessionAttributes({"mallId", "mallUrl"})
public class FuncUploadController {
	
	public static String UPLOAD_DIR = "upload-dir";
	public static String UPLOAD_DIR_PATH = "C://images";
	
	File f = new File(UPLOAD_DIR);
	// f.mkdir();
	
	@GetMapping(value = {"/", "/index"})
	public String index() {
		
		return "admin/funcupload/index.jsp";
	}
	
	@PostMapping(value = "/single_upload")
    public String fileUpload(@RequestParam("name") String name,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
		
		System.out.println("/single_upload ======> 들어옴");
		System.out.println("file ==> " + file.getOriginalFilename());
		System.out.println("fileContentType ==> " + file.getContentType());
		
        if (name.contains("/")) {
            redirectAttributes.addFlashAttribute("message", "Folder separators not allowed");
            return "redirect:/files";
        }
        
        if (!file.isEmpty()) {
            try {
            	String exeName = file.getOriginalFilename().split("\\.")[1];
            	//System.out.println("exeName ==> " + exeName[1]);
            	System.out.println("exeName ==> " + exeName);
            	
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File(UPLOAD_DIR_PATH + "/" + name + "." + exeName)));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded " + name + "!");
            }
            catch (Exception e) {
                redirectAttributes.addFlashAttribute("message",
                        "You failed to upload " + name + " => " + e.getMessage());
            }
        }
        else {
            redirectAttributes.addFlashAttribute("message",
                    "You failed to upload " + name + " because the file was empty");
        }

        //return "redirect:/funcupload/";
        return "success!!";
    }
	
	@GetMapping(value = "/multi")
	public String fileMultiUpload() {
		return "admin/funcupload/index_multi.jsp";
	}
	
	@PostMapping(value = "/multi")
	public ResponseEntity<?> fileMultiUpload(@RequestParam("file") MultipartFile[] files) {
		String fileName = null;
		String msg = "";
		
		if(files != null && files.length > 0) {
			for(int i=0; i < files.length; i++) {
				try {
					fileName = files[i].getOriginalFilename();
					byte[] bytes = files[i].getBytes();
					System.out.println("multi part fileName ====> " + fileName);
					BufferedOutputStream buffStream = 
							new BufferedOutputStream(new FileOutputStream(new File(UPLOAD_DIR_PATH + "/" + fileName)));
					buffStream.write(bytes);
					buffStream.close();
					msg += "success";
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<>("success", HttpStatus.BAD_REQUEST);
				}
			}
		}
		
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
}
