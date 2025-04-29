$("#passport-series-input").click().mask("99 99");
$("#passport-number-input").click().mask("999999");
$("#passport-department-code-input").click().mask("999-999");
$("#tin-input").click().mask("999999999999");


$("#lastname-input").on('input', lastname_input_handler);
$("#firstname-input").on('input', firstname_input_handler);
$("#middlename-input").on('input', middlename_input_handler);

$("#birthdate-input").on('change', birthdate_change_handler);

$("#return-index-link").on('click', return_index_link_handler);

//обработчик ввода фамилии
function lastname_input_handler() {

    let lastname_field = $("#lastname-input");
    let input_valid = check_input(lastname_field);
    highlight_invalid_field(lastname_field, input_valid);

}

//обработчик ввода имени
function firstname_input_handler() {

    let firstname_field = $("#firstname-input");
    let input_valid = check_input(firstname_field);
    highlight_invalid_field(firstname_field, input_valid);

}

//обработчик ввода отчества
function middlename_input_handler() {

    let middlename_field = $("#middlename-input");
    let input_valid = check_input(middlename_field);
    highlight_invalid_field(middlename_field, input_valid);
}

//Проверка корректности ввода
function check_input(element) {
    const regex = /^[А-Яа-яЁё\-\s]*$/g
    var input_value = element.val();

    return regex.test(input_value);
}

//Подсветка некорректно заполненного поля
function highlight_invalid_field(element, input_valid) {
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
	
	let inputDate = new Date(dateControl.val());
	inputDate.setHours(0, 0, 0, 0);
	
	let todayDate = new Date();
	todayDate.setHours(0, 0, 0, 0);
	
	let maxInputDate = new Date(todayDate);
	maxInputDate.setFullYear(maxInputDate.getFullYear() - 18);
	
	highlight_invalid_field(dateControl, inputDate < maxInputDate);
	
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