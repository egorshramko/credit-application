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
				.loanPurpose(app.getPurpose())
				.comment(app.getComment())
				.build();
		
		if (app.getTermValue() != null) {
			dto.setTermValue(app.getTermValue().intValue());
		}
		
		if (app.getTermUnit() != null) {
			dto.setTermUnit(app.getTermUnit().toString());
		}
		
		if (app.getAmount() != null) {
			dto.setLoanAmount(app.getAmount().intValue());
		}
		
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
