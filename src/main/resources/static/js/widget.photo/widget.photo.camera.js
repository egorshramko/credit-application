

export function camera_photo_btn_handler(event) {

    //вычисляем ширину и высоту камеры
    let camera_area = $("#camera-area");
    let width = camera_area.width; //ширина фотографии
    let height = 0; //вычислится на основе входящего потока

    //видеопоток активен
    let streaming = false;

    //ссылки на соответствующие элементы виджета
    let video_container = document.getElementById("camera-input-container");
    let video = document.getElementById("camera-input");
    let canvas = document.getElementById("camera-canvas");
    let photo = document.getElementById("loaded-photo");
    let camera_photo_btn = document.getElementById("take-photo-btn");
    let no_camera_img = document.getElementById("no-camera-img");

    //получаем медиапоток
    navigator.mediaDevices.getUserMedia({ video: true, audio: false })
        .then(function(stream) {
            video.srcObject = stream;
            video.play();
        })
        .catch(function(err) {
            console.error("Произошла ошибка: " + err);
        });

    video.addEventListener('canplay', function(event) {
        if (!streaming) {

            video_container.classList.remove('d-none');
            no_camera_img.classList.add('d-none');

            console.log("Запускаем работу вебки");

            height = video.videoHeight / (video.videoWidgth / width);

            //canvas.setAttribute('width', video.offsetWidth);
            //canvas.setAttribute('heigth', video.offsetHeight);
            
            streaming = true;
        }
    }, false);

    let take_photo_btn = $("#take-photo-btn");
    take_photo_btn.on('click', function (event) {
        take_picture();
        event.preventDefault();
    });
}

export function take_picture() {
    let canvas = document.getElementById("camera-canvas");
    let video = document.getElementById('camera-input');

    canvas.setAttribute('width', video.offsetWidth);
    canvas.setAttribute('height', video.offsetHeight);
    let width = canvas.width;
    let height = canvas.height;

    let canvas_context = canvas.getContext('2d');
    let no_camera_img = document.getElementById('no-camera-img');

    //если не светится заглушка веб-камеры, когда до нее нет доступа
    if (no_camera_img.classList.contains('d-none')) {
        canvas_context.drawImage(video, 0, 0, width, height);

        let photoUrl = canvas.toDataURL('/static/assets/camera/avatar.png');
        let loaded_photo = document.getElementById('loaded-photo');
        loaded_photo.setAttribute('src', photoUrl);
		
		//загружаем созданную фотографию на сервер
		sendPhoto(photoUrl);
		

        let photo_load_widget = document.getElementById('photo-load-widget');
        if (!photo_load_widget.classList.contains('photo-uploaded')) {
            photo_load_widget.classList.add('photo-uploaded');
        }
        
    }
    else {
        clear_photo();
    }
}

async function sendPhoto(url) {
	let formData = new FormData();
	let photoBlob = await fetch(url)
		.then(response => response.blob());
	formData.append("file", new File([photoBlob], 'avatar.png'));
	$.ajax({
		url: window.location.pathname + '/photo',
		data: formData, 
		cache: false, 
		contentType: false, 
		processData: false, 
		method: 'POST',
		success: (data) => {
			let photoLoadWidget = document.getElementById('photo-load-widget');
			photoLoadWidget.setAttribute('value', data);
		}
	});
}	

export function clear_photo() {
    $("#clear-photo-btn").blur();
    console.log("Сработала очистка фото");

    let loaded_photo = document.getElementById('loaded-photo');
	
    loaded_photo.setAttribute('src', '/static/assets/profile/empty_avatar.png');

    let photo_load_widget = document.getElementById('photo-load-widget');
    if (photo_load_widget.classList.contains('photo-uploaded')) {
        photo_load_widget.classList.remove('photo-uploaded');
    }
	
	//удаляем фото с сервера
	let photo_uuid = photo_load_widget.getAttribute('value');
	$.ajax({
		url: window.location.pathname + '/photo/' + photo_uuid, 
		method: 'DELETE',
		dataType: 'json',
		success: (data) => {
			if (data.removed) {
				photo_load_widget.removeAttribute('value');
			}
			else {
				console.warn("server returns removed: false");
			}
		},
		error: (exception) => {
			console.error(exception);
		}
	});
    
}

export function show_widget_controls() {
    let photo_widget_container = document.getElementById('photo-load-widget-container');
    let controls = photo_widget_container.querySelectorAll('.photo-widget-control');
    controls.forEach((control) => {
        if (control.classList.contains('hide-photo-widget-control')) {

            if (control.id != 'clear-photo-btn' || 
                control.id == 'clear-photo-btn' && 
                $("#photo-load-widget").hasClass('photo-uploaded')) {
                    
                control.classList.remove('hide-photo-widget-control');
            }
            
        }
    });
}

export function hide_widget_controls() {
    let photo_widget_container = document.getElementById('photo-load-widget-container');
    let controls = photo_widget_container.querySelectorAll('.photo-widget-control');
    controls.forEach((control) => {
        if (!control.classList.contains('hide-photo-widget-control')) {
            control.classList.add('hide-photo-widget-control');
        }
    });
}

