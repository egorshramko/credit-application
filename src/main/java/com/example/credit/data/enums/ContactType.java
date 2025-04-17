package com.example.credit.data.enums;

public enum ContactType {

	HOME("Домашний"), 
	ADDITIONAL("Дополнительный"), 
	MOBILE("Мобильный"), 
	WORK("Рабочий"),
	REGISTRATION_PLACE("По месту регистрации");

	private final String displayValue;

	ContactType(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

}
