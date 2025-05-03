package com.example.credit.web.api.dto.profile;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PassportDto {

	private String series;
	private String number;
	private String issueDate;
	private String departmentCode;
	private String issuePlace;
	
	private final List<PassportScanDto> scans = new ArrayList<>();
	
}
