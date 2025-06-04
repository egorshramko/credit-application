package com.example.credit.check.data;

import lombok.Data;
import com.example.credit.check.data.enums.ResultType;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Data
public class CheckResult {
	
	private ResultType resultType;
	private String checkName;
	private String message;
	
	public JsonObject toJson() {
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		
		if (resultType != null) {
			objectBuilder.add("resultType", resultType.toString());
		}
		
		if (checkName != null) {
			objectBuilder.add("checkName", checkName);
		}
		
		if (message != null) {
			objectBuilder.add("message", message);
		}
		
		return objectBuilder.build();
		
	}
	
}
