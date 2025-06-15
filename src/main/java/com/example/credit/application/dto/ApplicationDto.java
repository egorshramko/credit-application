package com.example.credit.application.dto;

import lombok.Data;

@Data
public class ApplicationDto {
	
	private String currency;
	private int termValue;
	private String termUnit;
	private String loanProduct;
	private int loanAmount;
	private String loanPurpose;
	private String comment;
	
}
