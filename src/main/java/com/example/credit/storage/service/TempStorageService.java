package com.example.credit.storage.service;

import java.nio.file.Path;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface TempStorageService {
	
	//внешняя загрузка файла в хранилище (с клиента)
	UUID upload(MultipartFile media);
	
	//добавление файла в хранилище
	UUID add(UUID fileUUID, Path file);
	
	//удаление файла из хранилища
	Path remove(UUID fileUUID);
	
}
