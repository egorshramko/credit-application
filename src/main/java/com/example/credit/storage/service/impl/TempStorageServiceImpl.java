package com.example.credit.storage.service.impl;

import com.example.credit.storage.service.TempStorageService;
import com.example.credit.storage.data.TempStorage;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.nio.file.Path;

@Slf4j
@Service
public class TempStorageServiceImpl implements TempStorageService {

	@Autowired
	private TempStorage tempStorage;
	
	@Override
	public UUID upload(MultipartFile media) {
		log.info("media name: " + media.getOriginalFilename());
		
		String defaultTempDirectory = System.getProperty("java.io.tmpdir");
		log.info("temp dir: " + defaultTempDirectory);
		
		return UUID.randomUUID();
	}
	
	@Override
	public UUID add(UUID fileUUID, Path file) {
		return tempStorage.add(fileUUID, file);
	}
	
	@Override
	public Path remove(UUID fileUUID) {
		return tempStorage.remove(fileUUID);
	}
	
	
}
