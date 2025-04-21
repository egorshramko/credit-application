package com.example.credit.storage.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface TempStorageService {
	
	//внешняя загрузка файла в хранилище (с клиента)
	UUID upload(MultipartFile media) throws IOException;
	
	//метод на скачивание файла из хранилища
	Path download(UUID fileUUID) throws IOException;
	
	//добавление файла в хранилище
	UUID add(UUID fileUUID, Path file);
	
	//удаление файла из хранилища
	boolean remove(UUID fileUUID) throws IOException;
	
	//получить название файла по ID
	String getFileNameById(UUID fileUUID);
	
	
}
