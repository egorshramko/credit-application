package com.example.credit.storage.controller;

import com.example.credit.storage.service.TempStorageService;

import java.nio.file.Path;
import java.util.UUID;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import jakarta.validation.Valid;
import jakarta.json.Json;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/storage")
public class FileStorageController {
	
	
	@Autowired
	private TempStorageService storageService;

	@PostMapping(value = "/upload")
	public ResponseEntity<?> uploadFile(@RequestParam(value = "file", required = false) @Valid final MultipartFile media) {
		
		try {
			return new ResponseEntity<>(storageService.upload(media), HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value = "/download/{fileUUID}")
	public ResponseEntity<?> downloadFile(@PathVariable String fileUUID) {
		
		try {
			Path filePath = storageService.download(UUID.fromString(fileUUID));
			Resource fileResource = new FileSystemResource(
					filePath
				);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + storageService.getFileNameById(UUID.fromString(fileUUID)) + "\"");
			
			return ResponseEntity.ok()
					.headers(headers)
					.contentLength(filePath.toFile().length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(fileResource);
		}
		catch (IOException err) {
			log.error("Attempt to download non-existent file from temporary storage");
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(err.getMessage());
		}
		
	}
	
	@DeleteMapping(value = "/delete/{fileUUID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteFile(@PathVariable String fileUUID) {
		try {
			boolean removeResult = storageService.remove(UUID.fromString(fileUUID));
			return ResponseEntity.ok()
					.body(
						Json.createObjectBuilder()
						.add("removed", removeResult)
						.build()
						.toString()
					);
		}
		catch(IOException err) {
			err.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(
						Json.createObjectBuilder()
							.add("removed", false)
							.add("error", err.getMessage())
							.build()
							.toString()
					);
		}
	}
	
}
