package com.example.credit.storage.data;

import java.nio.file.Path;

import lombok.Data;

@Data
public class StorageFileWrapper {
	
	private final Path filePath;
	
	private final String originalName;
	
}
