package com.example.credit.web.api.mapper;

import com.example.credit.data.Passport;
import com.example.credit.data.BinaryContent;
import com.example.credit.data.PassportScan;
import com.example.credit.web.api.dto.profile.PassportDto;
import com.example.credit.web.api.dto.profile.PassportScanDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PassportMapper {
	
	@Autowired
	private BinaryContentMapper binaryContentMapper;
	
	public Passport toPassport(PassportDto passportDto) {
		
		List<PassportScan> scansList = new ArrayList<>();
		
		if (passportDto.getScans() != null && passportDto.getScans().size() > 0) {
			for (PassportScanDto scanDto : passportDto.getScans()) {
				BinaryContent scanFile = binaryContentMapper.toBinaryContent(scanDto.getId());
				
				scansList.add(
					PassportScan.builder()
						.scanFile(scanFile)
						.build()
				);
			}
		}
		
		return Passport.builder()
				.series(passportDto.getSeries())
				.number(passportDto.getNumber())
				.issueDate(LocalDate.parse(passportDto.getIssueDate()))
				.departmentCode(passportDto.getDepartmentCode())
				.issuePlace(passportDto.getIssuePlace())
				.scans(scansList)
				.build();
	}
	
	public Passport updatePassportFromDto(Passport passport, PassportDto passportDto) {
		
		passport.setSeries(passportDto.getSeries());
		passport.setNumber(passportDto.getNumber());
		passport.setIssueDate(LocalDate.parse(passportDto.getIssueDate(), 
				DateTimeFormatter.ISO_LOCAL_DATE));
		passport.setIssuePlace(passportDto.getIssuePlace());
		passport.setDepartmentCode(passportDto.getDepartmentCode());
		
		List<PassportScan> passportScans = new ArrayList<>();
		for (PassportScanDto scanDto : passportDto.getScans()) {
			BinaryContent binaryScan = binaryContentMapper.toBinaryContent(scanDto.getId());
			passportScans.add(PassportScan.builder()
					.scanFile(binaryScan)
					.build());
		}
		
		passport.setScans(passportScans);
		
		return passport;
	}
	
}
