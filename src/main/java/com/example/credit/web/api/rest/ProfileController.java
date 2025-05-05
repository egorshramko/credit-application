package com.example.credit.web.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.credit.web.api.dto.profile.ClientProfileDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(path = "/api/credit/{id}/profile", produces = "application/json")
public class ProfileController {
	
	/*
	 * TODO:
	 * 1. Сохранить анкету
	 *    Вызов сервиса управления анкетой
	 * 2. Обновить данные клиента по данным анкеты
	 *    Вызов сервиса управления клиентами
	 * 3. Вызвать проверку бизнес-правил
	 *    Нужен сервис проверки бизнес-правил (желательно способный к расширению)
	 * 4. Сформировать ответ по результатам проверки
	 *    Ответ должен быть в формате JSON
	 * 4.1. Проверки успешны, создаем заявку, меняем этап оформления
	 * 4.2. Проверки неуспешны, формируем сообщение уведомления
	 * 
	 * Метод обработки отправленной анкеты
	 */
	@PostMapping("/verificate")
	@ResponseBody
	public ResponseEntity<String> updateProfileData(
		@PathVariable("id") String creditId,
		@RequestBody ClientProfileDto profileDto
	) {
		
		
		
		return null;
	}
	
}
