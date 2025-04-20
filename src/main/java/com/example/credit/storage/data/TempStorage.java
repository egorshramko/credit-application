package com.example.credit.storage.data;

import java.util.UUID;
import java.nio.file.Path;

public interface TempStorage {
	
	UUID add(UUID fileUUID, StorageFileWrapper file);
	StorageFileWrapper remove(UUID fileUUID);
	StorageFileWrapper get(UUID fileUUID);
	
}
