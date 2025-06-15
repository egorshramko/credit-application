package com.example.credit.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.credit.application.data.Application;
import com.example.credit.application.repository.ApplicationRepository;
import com.example.credit.data.Credit;
import com.example.credit.service.CreditService;

@SpringBootTest
public class ApplicationServiceImplTest {
	
	@Autowired
	private ApplicationService applicationService;
	
	@Mock
	private CreditService creditService;
	
	@Mock
	private ApplicationRepository appRepository;
	
	@Test
	public void getApplicationByCreditId_whenApplicationNotExist_thenReturnNewApplication() {
		Mockito.when(creditService.getCreditById("1")).thenReturn(getCreditWithoutApplication());
		Mockito.when(appRepository.save(new Application())).thenReturn(getNewApplication());
		Mockito.when(creditService.saveCredit(Mockito.any()))
				.thenReturn(Credit.builder()
						.application(getNewApplication())
						.build());
		
		Application app = applicationService.getApplicationByCreditId("1");
		
		Assertions.assertNotNull(app);
		Assertions.assertEquals(app.getId(), Long.valueOf(1));
		
	}
	
	@Test
	public void getApplicationByCreditId_whenApplicationIsExist_thenReturnThisApplication() {
		Mockito.when(creditService.getCreditById("1")).thenReturn(getCreditWithNewApplication());
		
		Application app = applicationService.getApplicationByCreditId("1");
		
		Assertions.assertNotNull(app);
		Assertions.assertEquals(app.getId(), Long.valueOf(1));
	}
	
	private Credit getCreditWithoutApplication() {
		return Credit.builder()
				.build();
	}
	
	private Credit getCreditWithNewApplication() {
		return Credit.builder()
				.application(getNewApplication())
				.build();
	}
	
	private Application getNewApplication() {
		return Application.builder()
				.id(Long.valueOf(1))
				.build();
	}

}
