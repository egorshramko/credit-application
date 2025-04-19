package com.example.credit.storage.data;

import java.util.UUID;
import java.nio.file.Path;

public interface TempStorage {
	
	UUID add(UUID fileUUID, Path file);
	Path remove(UUID fileUUID);
	
}
