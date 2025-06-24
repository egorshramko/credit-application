package com.example.credit.application.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class ApplicationDto {
	
	private String currency;
	private int termValue;
	private String termUnit;
	private String loanProduct;
	private int loanAmount;
	private String loanPurpose;
	private String comment;
	
}
