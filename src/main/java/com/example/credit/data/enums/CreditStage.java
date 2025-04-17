package com.example.credit.data.enums;

public enum CreditStage {
	CREDIT_FORM("Заполнение анкеты"), CREDIT_APPLICATION("Заполнение заявки"), AGREEMENT_SIGNING("Подписание договора"),
	REJECT("Отказ"), COMPLETED("Завершено");

	private final String displayValue;

	CreditStage(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}
}
