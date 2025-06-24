package com.example.credit.application.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.credit.application.data.Application;
import com.example.credit.application.data.LoanProduct;
import com.example.credit.application.data.enums.Currency;
import com.example.credit.application.data.enums.TermUnit;
import com.example.credit.application.dto.ApplicationDto;

@SpringBootTest
public class ApplicationMapperTest {
	
	@Autowired
	private ApplicationMapper applicationMapper;
	
	@Test
	public void toDto_whenApplicationFullFilled_thenReturnFullFilledDto() {
		Application app = getFullFilledApplication();
		
		ApplicationDto dto = applicationMapper.toDto(app);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(getFullFilledDto(), dto);
		
	}
	
	@Test
	public void toDto_whenApplicationWithoutComment_thenReturnDtoWithoutComment() {
		Application app = getFullFilledApplication();
		app.setComment(null);
		
		ApplicationDto dto = applicationMapper.toDto(app);
		
		ApplicationDto expectedDto = getFullFilledDto();
		expectedDto.setComment(null);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(expectedDto, dto);
	}
	
	private ApplicationDto getFullFilledDto() {
		return ApplicationDto.builder()
				.currency("RUB")
				.termValue(24)
				.termUnit("MONTH")
				.loanProduct("Test product")
				.loanAmount(Integer.valueOf(700000))
				.loanPurpose("Test purpose")
				.comment("Test comment")
				.build();
	}
	
	private Application getFullFilledApplication() {
		return Application.builder()
				.id(Long.valueOf(1))
				.amount(Integer.valueOf(700000))
				.termUnit(TermUnit.MONTH)
				.termValue(Integer.valueOf(24))
				.purpose("Test purpose")
				.product(getLoanProduct())
				.comment("Test comment")
				.build();
	}
	
	private LoanProduct getLoanProduct() {
		return new LoanProduct(
				Long.valueOf(1),
				"Test product",
				Double.valueOf(12.0),
				Integer.valueOf(12),
				Integer.valueOf(36),
				Integer.valueOf(500000),
				Integer.valueOf(1000000),
				Currency.RUB
				);
	}
	
}
