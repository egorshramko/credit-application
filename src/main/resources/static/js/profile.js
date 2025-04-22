$("#passport-series-input").click().mask("99 99");
$("#passport-number-input").click().mask("999999");
$("#passport-department-code-input").click().mask("999-999");
$("#tin-input").click().mask("999999999999");


$("#lastname-input").on('input', lastname_input_handler);
$("#firstname-input").on('input', firstname_input_handler);
$("#middlename-input").on('input', middlename_input_handler);

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

async function return_index_link_handler(event) {
	
	let link = event.target;
	
	console.log("Кликнули на возврат на главную");	
	
	//перед возвращением на главную страницу, необходимо 
	//удалить все подгруженные файлы из временного хранилища (все сканы и фото)
	let photo_load_widget = document.getElementById('photo-load-widget');
	let avatar_uuid = photo_load_widget.getAttribute('value');
	
	let uuids = [];
	if (photo_load_widget != null) {
		uuids.push(avatar_uuid);
	}
	
	let scans_collection_container = document.getElementById('scans-collection-container');
 	scans_collection_container.childNodes.forEach((node) => {
		
		try {
			let node_uuid = node.getAttribute('value');
			if (node_uuid != null) {
				uuids.push(node_uuid);
			} 
		}
		catch (err) {
			console.log("Обработано исключение");
		}
		
	});
	
	uuids.forEach((uuid) => {
		
		removeFilesByUuid(uuid);
		
	});
	
	window.location = link.href;
}

function removeFilesByUuid(uuid) {
	$.ajax({
		url: '/storage/delete/' + uuid,
		method: 'DELETE',
		success: (data) => {
			if (data.removed) {
				console.log('resource removed');
			}
			else {
				console.log('resource not removed');
			}
		}
	});
}