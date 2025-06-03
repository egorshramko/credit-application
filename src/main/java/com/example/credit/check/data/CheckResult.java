package com.example.credit.check.data;

import lombok.Data;
import com.example.credit.check.data.enums.ResultType;

@Data
public class CheckResult {
	
	private ResultType resultType;
	private String message;
	
}
