package com.cafe24.mammoth.app.support;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 업로드 시 디렉토리 생성, 파일 저장을 지원하는 컴포넌트 클래스
 * @author MoonStar, Allen
 *
 */
@Component
public class FileUploader {
	@Deprecated
	public List<String> restoreFiles(String path, MultipartFile[] files) {
		List<String> filePaths = new ArrayList<String>();
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				try {
					String fileName = files[i].getOriginalFilename();
					byte[] bytes = files[i].getBytes();
					String filePath = path + "/" + fileName;
					filePaths.add(filePath);
					BufferedOutputStream buffStream = new BufferedOutputStream(
							new FileOutputStream(new File(filePath)));
					buffStream.write(bytes);
					buffStream.close();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return filePaths;
	}
	
	public String restoreFile(String path, MultipartFile file) {
		String filePath = null;
		
		try {
			String fileName = file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			filePath = path + "/" + fileName;
			BufferedOutputStream buffStream = new BufferedOutputStream(
					new FileOutputStream(new File(filePath)));
			buffStream.write(bytes);
			buffStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return filePath;
	}

	public String makeDirectory(String rootPath, String directoryName) {
		String filePath = rootPath + "/" + directoryName;
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		return filePath;
	}
}
