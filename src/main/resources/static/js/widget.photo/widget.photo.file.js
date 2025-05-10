export function file_btn_click_handler() {
    let file_photo_input = document.getElementById('file-photo-input').click();
}

export function file_uploaded(event) {
    console.log('Загрузили файл');

    

    let last_uploaded_file = event.target.files[0];

    console.log(last_uploaded_file);

    if (valid_file_type(last_uploaded_file)) {
        console.log("Тип файла корректен");
		
		let form_data = new FormData();
		form_data.append("file", last_uploaded_file);

		//загружаем файл на сервер
		$.ajax({
			url: window.location.pathname + '/addPhoto',
			data: form_data,
			cache: false, 
			contentType: false, 
			processData: false, 
			method: 'POST',
			success: (data) => {
				let photoLoadWidget = document.getElementById('photo-load-widget');
				photoLoadWidget.setAttribute('value', data);
			}
		});
		
        let photo_preview = document.getElementById('loaded-photo');
        photo_preview.setAttribute('src', window.URL.createObjectURL(last_uploaded_file));

        let photo_load_widget = document.getElementById('photo-load-widget');
        if (!photo_load_widget.classList.contains('photo-uploaded')) {
            photo_load_widget.classList.add('photo-uploaded');
        }

    }
    else {
        console.log("Тип файла некорректен");
    }
    
}

function valid_file_type(file) {

    let file_types = ["image/jpeg", "image/pjpeg", "image/png"];

    return file_types.filter((file_type) => file.type === file_type).length > 0;

}