package com.example.credit.web.api.rest;

import org.springframework.web.bind.annotation.RestController;

import com.example.credit.web.api.dto.profile.ClientProfileDto;
import com.example.credit.data.ClientProfile;
import com.example.credit.service.ClientProfileService;
import com.example.credit.service.CreditService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api/credit/{id}/profile", produces = "application/json")
public class ProfileController {

	@Autowired
	private CreditService creditService;
	
	@Autowired
	private ClientProfileService clientProfileService;
	
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
	public ResponseEntity<String> handleProfileForm(
		@RequestBody ClientProfileDto profileDto,
		@PathVariable("id") String creditId
	) {
		
		//обновление данных анкеты и их сохранение
		ClientProfile profile = creditService.getProfileByCreditId(Long.parseLong(creditId));
		clientProfileService.updateProfile(profile, profileDto);
		
		return ResponseEntity.ok().build();
	}
	
	
}
