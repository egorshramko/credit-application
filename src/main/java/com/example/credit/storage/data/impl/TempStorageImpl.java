package com.example.credit.storage.data.impl;

import com.example.credit.storage.data.TempStorage;
import com.example.credit.storage.data.StorageFileWrapper;

import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TempStorageImpl implements TempStorage {

	private Map<UUID, StorageFileWrapper> tempStorageFiles;
	
	public TempStorageImpl() {
		tempStorageFiles = new ConcurrentHashMap<>();
	}
	
	@Override
	public UUID add(UUID fileUUID, StorageFileWrapper file) {
		
		if (fileUUID == null) {
			fileUUID = UUID.randomUUID();
		}
		
		tempStorageFiles.put(fileUUID, file);
		return fileUUID;
	}
	
	@Override
	public StorageFileWrapper remove(UUID fileUUID) {
		return tempStorageFiles.remove(fileUUID);
	}
	
	@Override
	public StorageFileWrapper get(UUID fileUUID) {
		return tempStorageFiles.get(fileUUID);
	}
	
}
