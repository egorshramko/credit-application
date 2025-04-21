package com.example.credit.storage.service.impl;

import com.example.credit.storage.service.TempStorageService;
import com.example.credit.storage.data.TempStorage;
import com.example.credit.storage.data.StorageFileWrapper;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.io.InputStream;
import java.io.IOException;

@Slf4j
@Service
public class TempStorageServiceImpl implements TempStorageService {

	@Autowired
	private TempStorage tempStorage;
	
	@Override
	public UUID upload(MultipartFile media) throws IOException {
		log.info("media name: " + media.getOriginalFilename());
		
		Path defaultTempDirectory = Path.of(System.getProperty("java.io.tmpdir"));
		log.info("temp dir: " + defaultTempDirectory);
		
		try (InputStream mediaStream = media.getInputStream()) {
			
			UUID uploadedFileUUID = UUID.randomUUID();
			Path uploadedFilePath = defaultTempDirectory.resolve(uploadedFileUUID.toString() + 
					this.getOriginalFileExtension(media));
			log.info("uploadedFilePath: " + uploadedFilePath.toString());
			
			//обертка вокруг файла для того, чтобы знать какое настоящее имя файла
			StorageFileWrapper fileWrapper = new StorageFileWrapper(uploadedFilePath, media.getOriginalFilename());
			
			if (!Files.exists(uploadedFilePath)) {
				Files.createFile(uploadedFilePath);
			}
			
			Files.copy(mediaStream, uploadedFilePath, StandardCopyOption.REPLACE_EXISTING);
			
			tempStorage.add(uploadedFileUUID, fileWrapper);
			
			return uploadedFileUUID;
		}
		catch (IOException err) {
			err.printStackTrace();
			throw new IOException(err);
		}
		
	}
	
	@Override
	public Path download(UUID fileUUID) throws IOException {
		
		return tempStorage.get(fileUUID).getFilePath();
		
	}
	
	@Override
	public UUID add(UUID fileUUID, Path file) {
		
		return tempStorage.add(
				fileUUID, 
				new StorageFileWrapper(
						file,
						file.getFileName().toString()
					)
				);
	}
	
	@Override
	public boolean remove(UUID fileUUID) throws IOException {
		return tempStorage.remove(fileUUID);
	}
	
	private String getOriginalFileExtension(MultipartFile media) {
		
			String originalFilename = media.getOriginalFilename();
			
			if (originalFilename != null) {
				return originalFilename.substring(
						originalFilename.lastIndexOf('.'),
						originalFilename.length()
					);
			}
			else {
				return "";
			}
		
	}
	
	@Override
	public String getFileNameById(UUID fileUUID) {
		
		try {
			return tempStorage.get(fileUUID).getOriginalName();
		}
		catch (IOException err) {
			err.printStackTrace();
		}
		
		return "";
		
	}
	
	
}
