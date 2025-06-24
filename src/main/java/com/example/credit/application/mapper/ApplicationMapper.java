package com.example.credit.application.mapper;

import com.example.credit.application.dto.ApplicationDto;
import com.example.credit.application.data.Application;
import com.example.credit.application.data.LoanProduct;
import com.example.credit.application.data.enums.Currency;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

	public ApplicationDto toDto(Application app) {
		ApplicationDto dto = ApplicationDto.builder()
				.termValue(app.getTermValue().intValue())
				.termUnit(app.getTermUnit().toString())
				.loanAmount(app.getAmount().intValue())
				.loanPurpose(app.getPurpose())
				.comment(app.getComment())
				.build();
		
		LoanProduct appProduct = app.getProduct();
		if (appProduct != null) {
			
			if (appProduct.getCurrency() != null) {
				dto.setCurrency(appProduct.getCurrency().toString());
			}
			
			if (appProduct.getName() != null) {
				dto.setLoanProduct(appProduct.getName());
			}
			
		}
		
		return dto;
	}
	
}
