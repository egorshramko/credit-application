package com.example.credit.web.api.mapper;

import com.example.credit.data.BinaryContent;
import com.example.credit.storage.service.TempStorageService;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BinaryContentMapper {
	
	@Autowired
	private TempStorageService tempStorageService;
	
	public BinaryContent toBinaryContent(String contentId) {
		
		try {
			
			UUID contentUUID = UUID.fromString(contentId);
			
			return BinaryContent.builder()
					.name(tempStorageService.getFileNameById(contentUUID))
					.content(tempStorageService.download(contentUUID).toFile())
					.uuid(contentUUID)
					.build();
		}
		catch (IOException ioException) {
			log.error("Error while mapping file of photo from profileDto");
			log.error(ioException.getMessage());
			return null;
		}
		
	}
	
}
