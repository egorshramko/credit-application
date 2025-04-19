package com.example.credit.storage.data.impl;

import com.example.credit.storage.data.TempStorage;

import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TempStorageImpl implements TempStorage {

	private Map<UUID, Path> tempStorageFiles;
	
	public TempStorageImpl() {
		tempStorageFiles = new ConcurrentHashMap<>();
	}
	
	@Override
	public UUID add(UUID fileUUID, Path file) {
		
		if (fileUUID == null) {
			fileUUID = UUID.randomUUID();
		}
		
		tempStorageFiles.put(fileUUID, file);
		return fileUUID;
	}
	
	@Override
	public Path remove(UUID fileUUID) {
		return tempStorageFiles.remove(fileUUID);
	}
	
}
