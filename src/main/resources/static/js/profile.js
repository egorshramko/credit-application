$("#passport-series-input").click().mask("99 99", {
	completed: function() {
		passport_series_input_handler();
	},
	autoclear: false
});

$("#passport-number-input").click().mask("999999", {
	completed: function() {
		passport_number_input_handler();
	},
	autoclear: false
});
$("#passport-department-code-input").click().mask("999-999", {
	completed: function() {
		passport_department_code_input_handler();
	},
	autoclear: false
});
$("#tin-input").click().mask("999999999999");


$("#lastname-input").on('input', lastname_input_handler);
$("#firstname-input").on('input', firstname_input_handler);
$("#middlename-input").on('input', middlename_input_handler);

$("#birthdate-input").on('change', birthdate_change_handler);

$("#citizenship-input").on('input', citizenship_input_handler);
$("#sex-input").on('change', sex_change_handler);

$("#passport-issue-date-input").on('change', passport_issue_date_input_handler);
$("#passport-issue-place-input").on('input', passport_issue_place_input_handler);

$("#consent-personal-data-input").on('change', consent_personal_data_input_handler);

$("#return-index-link").on('click', return_index_link_handler);
$("#send-profile-btn").on('click', sendProfileHandler);

//обработчик ввода фамилии
function lastname_input_handler() {

    let lastname_field = $("#lastname-input");
    let input_valid = check_input(lastname_field);
    highlight_invalid_field(lastname_field, input_valid, "Допустимые символы: кириллица, -, пробел");

}

//обработчик ввода имени
function firstname_input_handler() {

    let firstname_field = $("#firstname-input");
    let input_valid = check_input(firstname_field);
    highlight_invalid_field(firstname_field, input_valid, "Допустимые символы: кириллица, -, пробел");

}

//обработчик ввода отчества
function middlename_input_handler() {

    let middlename_field = $("#middlename-input");
    let input_valid = check_input(middlename_field);
    highlight_invalid_field(middlename_field, input_valid, "Допустимые символы: кириллица, -, пробел");
}

function citizenship_input_handler() {
	let citizenship_field = $("#citizenship-input");
	highlight_invalid_field(citizenship_field);
}

function sex_change_handler() {
	let sex_field = $("#sex-input");
	highlight_invalid_field(sex_field);
}

function passport_series_input_handler() {
	
	let passport_series = $("#passport-series-input");
	highlight_invalid_field(passport_series);
}

function passport_number_input_handler() {
	let passport_number = $("#passport-number-input");
	highlight_invalid_field(passport_number);
}

function passport_issue_date_input_handler() {
	
	let dateControl = $('#passport-issue-date-input');
		
		if (!!dateControl.val()) {
			let inputDate = new Date(dateControl.val());
			inputDate.setHours(0, 0, 0, 0);
				
			let todayDate = new Date();
			todayDate.setHours(0, 0, 0, 0);
				
			highlight_invalid_field(dateControl, inputDate <= todayDate, "Пожалуйста, введите корректную дату");
			
		}
		else {
			highlight_invalid_field(dateControl);
		}
		
}

function passport_department_code_input_handler() {
	let passport_department_code = $("#passport-department-code-input");
	console.log(passport_department_code.val());
	highlight_invalid_field(passport_department_code);
}

function passport_issue_place_input_handler() {
	let passport_issue_place = $("#passport-issue-place-input");
	highlight_invalid_field(passport_issue_place);
}

function consent_personal_data_input_handler() {
	console.log("Кликнули на согласие на обработку данных")
	let consent_personal_data_input = $("#consent-personal-data-input");
	let fill_profile_btn = $("#send-profile-btn");
	
	if (!!consent_personal_data_input.is(":checked") && 
		fill_profile_btn.hasClass("disabled")) {
	
		fill_profile_btn.removeClass("disabled");
	}
	else {
		fill_profile_btn.addClass("disabled");
	}
	
	highlight_invalid_field(consent_personal_data_input);
}

//Проверка корректности ввода
function check_input(element) {
    const regex = /^[А-Яа-яЁё\-\s]*$/g
    var input_value = element.val();

    return regex.test(input_value);
}

/**
 * Функция управления подсветкой некорректности элемента страницы
 * Подсвечивает красным поле, если условие input_valid = false, снимает подсветку в противном случае
 * 
 * Параметры:
 * element - jquery объект элемента страницы
 * input_valid - необязательный булевый параметр для управления подсветкой
 * message - необязательный параметр. Отображаемое сообщение в случае некорректности поля
 * 
 */
function highlight_invalid_field(element, input_valid = true, message = "") {
    
	//родительский контейнер
	let parent_div = element.parent();
	let invalid_feedback_div = parent_div.find(".invalid-feedback");
	let invalid_feedback_span = invalid_feedback_div.find("span");
	invalid_feedback_span.text(message);
	
	if (!input_valid) {
        if (!element.hasClass('is-invalid')) {
            element.addClass('is-invalid');
        }
    }
    else {
        if (element.hasClass('is-invalid')) {
            element.removeClass('is-invalid');
        }
    }
}

//обработчик смены даты рождения
function birthdate_change_handler(event) {
	
	let dateControl = $('#birthdate-input');
	
	if (!!dateControl.val()) {
		let inputDate = new Date(dateControl.val());
		inputDate.setHours(0, 0, 0, 0);
			
		let todayDate = new Date();
		todayDate.setHours(0, 0, 0, 0);
			
		let maxInputDate = new Date(todayDate);
		maxInputDate.setFullYear(maxInputDate.getFullYear() - 18);
			
		highlight_invalid_field(dateControl, inputDate < maxInputDate, "Клиент должен быть старше 18 лет");
		
		if (inputDate > todayDate) {
			highlight_invalid_field(dateControl, false, "Пожалуйста, введите корректную дату");
		}
	}
	else {
		highlight_invalid_field(dateControl);
	}
	
	
	
	
}

async function return_index_link_handler(event) {
	
	event.preventDefault();
	let link = event.target;
	
	console.log("Кликнули на возврат на главную");	
	
	//перед возвращением на главную страницу, необходимо 
	//удалить все подгруженные файлы из временного хранилища (все сканы и фото)
	let photo_load_widget = document.getElementById('photo-load-widget');
	let avatar_uuid = photo_load_widget.getAttribute('value');
	
	let uuids = [];
	if (avatar_uuid != null) {
		uuids.push(avatar_uuid);
	}
	
	let scans_collection_container = document.getElementById('scans-collection-container');
	let scan_controls = scans_collection_container.querySelectorAll('.scan-element');
	
	scan_controls.forEach((control) => {
		let control_value = control.getAttribute('value');
		if (!!control_value) {
			uuids.push(control_value);
		}
	});
	
	console.log("removing elements: ");
	console.log(uuids);
	console.log("uuids.length: " + uuids.length);
	
	let removedFiles = 0;
	if (uuids == null || uuids.length == 0) {
		window.location = link.href;
	}
	else {
		uuids.forEach((uuid) => {
				
			removeFileByUuid(uuid)
				.then(() => {
					removedFiles++;
					if (removedFiles == uuids.length) {
						window.location = link.href;
					}
				});
				
		});
	}
	
	
}

async function removeFileByUuid(uuid) {
	
	let response = false;
	await $.ajax({
		url: '/storage/delete/' + uuid,
		method: 'DELETE',
		success: (data) => {
			if (data.removed) {
				console.log('resource ' + uuid + ' removed');
			}
			else {
				console.log('resource ' + uuid + ' not removed');
			}
			response = data.removed;
		}
	});
	
	return response;
}

//обработчик нажатия на кнопку "Заполнить анкету"
async function sendProfileHandler(event) {
	
	console.log("Валидация обязательных полей");
	
	let formValid = validateProfileForm();
	
	if (formValid) {
		console.log("Все обязательные поля заполнены, формируем запрос на сервер");
		hideErrorAlert();
		
		let formDataJson = mapFormData();
		console.log(formDataJson);
		
		await $("#please-wait-dialog").modal('show');
		
		//раскомменчу, когда разберусь с модальным окошком
		sendFormDataJson(formDataJson)
			.then(() => {
				$("#please-wait-dialog").modal('hide');
			})
			.catch(() => {
				$("#please-wait-dialog").modal('hide');
			});
		
	}
	else {
		console.log("Некоторые обязательные поля не заполнены");
		
		showErrorAlert("Ошибка заполнения анкеты. Пожалуйста, исправьте ошибки на форме, а затем повторите попытку.");
		
	}
	
}

//функция 
async function sendFormDataJson(formDataJson) {
	console.log("Отправка запроса на сервер");
	
	let response = await fetch(window.location.pathname, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json;charset=utf-8'
		},
		body: formDataJson
	});
	
	console.log(response);
}

//функция показа уведомления об ошибке
function showErrorAlert(message) {
	
	$("#error-alert-container").load('/static/html/profile.error.alert.html', () => {
		$("#error-alert-container .alert-container").html(message);
	});
	
	console.log($("#error-alert-container"));
	
	window.scrollTo(0, 0);
}

//функция скрытия уведомления об ошибке
function hideErrorAlert() {
	$("#error-alert-container").empty();
}

//функция маппинга данных из формы
function mapFormData() {
	let formData = {};
	formData.lastname = $("#lastname-input").val();
	formData.firstname = $("#firstname-input").val();
	formData.middlename = $("#middlename-input").val();
	formData.birthdate = $("#birthdate-input").val();
	formData.citizenship = $("#citizenship-input").val();
	formData.sex = $("#sex-input").val();
	
	if (!!$("#photo-load-widget").attr("value")) {
		formData.photo = $("#photo-load-widget").attr('value');
		console.log(formData.photo);
	}
	
	formData.passport = {};
	formData.passport.series = $("#passport-series-input").val();
	formData.passport.number = $("#passport-number-input").val();
	formData.passport.issueDate = $("#passport-issue-date-input").val();
	formData.passport.departmentCode = $("#passport-department-code-input").val();
	formData.passport.issuePlace = $("#passport-issue-place-input").val();
	
	if ($("#scans-collection-container").find(".scan-element").length > 0) {
		formData.passport.scans = [];
		
		$("#scans-collection-container").find(".scan-element").each((i, scan) => {
			let scanData = {};
			
			console.log("scan");
			console.log(scan);
			scanData.id = scan.getAttribute('value');
			formData.passport.scans.push(scanData);
		});
	}
	
	if ($("#contacts-repeater").find(".contact-element").length > 0) {
		
		formData.contacts = [];
		
		$("#contacts-repeater").find(".contact-element").each((i, contact) => {
			
			let contactElementId = contact.getAttribute('id');
			contactElementId = contactElementId.replace('contact-', '');
			
			let contactData = {};
			contactData.contactType = $("#contact-type-input-" + contactElementId).val();
			contactData.phoneNumber = $("#contact-value-" + contactElementId).val();
			contactData.comment = $("#contact-comment-" + contactElementId).val();
			
			formData.contacts.push(contactData);
		});
		
	}
	
	if (!!$("#tin-input").val()) {
		formData.tin = $("#tin-input").val();
	}
	
	if (!!$("#comment-input").val()) {
		formData.comment = $("#comment-input").val();
	}
	
	formData.consentPersonalData = $("#consent-personal-data-input").is(":checked");
	
	return JSON.stringify(formData);
}

/*
 * Функция проверки заполнения текстовых полей.
 * В случае, если поле пустое, оно подсвечивается красным и отображает сообщение
 * 
 * Параметры:
 *  element - jquery объект типа input
 *  baseMessage - базовое сообщение, которое будет выводиться, 
 * 		если поле уже подсвечено красным и все еще некорректно (необязательный параметр)
 *  additionalCheck - дополнительные условия проверки (необязательный параметр)
 */
function validateField(element, baseMessage = "", additionalChecks = true) {
	let elementValue = element.val();
	let elementInvalid = elementValue == "" || elementValue == null ||
		elementValue == undefined || element.hasClass('is-invalid') || !additionalChecks;
		
	if (elementInvalid) {
		
		let invalidMessage = baseMessage;
		if (!element.hasClass('is-invalid') || invalidMessage == "") {
			invalidMessage = "Пожалуйста, заполните обязательное поле";
		}
		
		highlight_invalid_field(element, !elementInvalid, invalidMessage);
		
		return false;
	}
	
	highlight_invalid_field(element, !elementInvalid, "");
	
	return true;
}

//функция проверки обязательных полей заполнения анкеты
function validateProfileForm() {
	let validated = true;
	
	//проверка фамилии
	if (!validateField($("#lastname-input"), "Допустимые символы: кириллица, -, пробел")) {
		validated = false;
	}
	
	//проверка имени
	if (!validateField($("#firstname-input"), "Допустимые символы: кириллица, -, пробел")) { 
		validated = false;
	}
	
	//проверка даты рождения
	if (!validateField($("#birthdate-input"), "Клиент должен быть старше 18 лет")) {
		validated = false;
	}
	
	//проверка гражданства
	if (!validateField($("#citizenship-input"))) {
		validated = false;
	}
	
	//проверка пола
	if (!validateField($("#sex-input"))) {
		validated = false;
	}
	
	//проверка серии паспорта
	if (!validateField($("#passport-series-input"), 
		undefined, 
		/^\d{2}\s{1}\d{2}$/g.test($("#passport-series-input").val()))) {
		
		validated = false;
	}
	
	//проверка номера паспорта
	if (!validateField($("#passport-number-input"),
		undefined,
		/^\d{6}$/g.test($("#passport-number-input").val()))) {
		
		validated = false;
	}
	
	//проверка даты выдачи паспорта
	if (!validateField($("#passport-issue-date-input"))) {
		validated = false;
	}
	
	//проверка кода подразделения
	if (!validateField($("#passport-department-code-input"),
		undefined,
		/^\d{3}\-{1}\d{3}$/g.test($("#passport-department-code-input").val()))) {
		validated = false;
	}
	
	//проверка места выдачи паспорта
	if (!validateField($("#passport-issue-place-input"))) {
		validated = false;
	}
	
	//проверка согласия на обработку персональных данных
	let consentPersonalDataCheckbox = $("#consent-personal-data-input");
	let givesConsentPersonalData = consentPersonalDataCheckbox.is(":checked");
	if (!givesConsentPersonalData) {
		highlight_invalid_field(consentPersonalDataCheckbox, givesConsentPersonalData, "Пожалуйста, дайте согласие на обработку данных");
	}
	
	
	return validated;
}