package com.example.credit.storage.data;

import java.util.UUID;

import java.io.IOException;

public interface TempStorage {
	
	UUID add(UUID fileUUID, StorageFileWrapper file);
	boolean remove(UUID fileUUID) throws IOException;
	StorageFileWrapper get(UUID fileUUID) throws IOException;
	
}
