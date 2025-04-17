package com.example.credit.data.enums;

public enum Sex {
	MALE("Мужской"), FEMALE("Женский");

	private final String displayValue;

	Sex(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

}
