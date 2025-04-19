export function addFileBtnHandler() {
    console.log('Тыкнули на добавление файла');

    let scansControl = document.getElementById('scans-control');

    scansControl.click();
}

/**
 * Функция запускается по событию выбора файла
 */
export function selectFileHandler(event) {
	
	console.log("Выбрали файл");
	
	let scansControl = event.target;
	let lastUploadedFile = scansControl.files[0];
	console.log(lastUploadedFile);
	
	let data = new FormData();
	data.append("file", lastUploadedFile);
	
	$.ajax({
		url: '/storage/upload',
		data: data,
		cache: false, 
		contentType: false, 
		processData: false, 
		method: 'POST',
		success: (data) => {
			console.log(data);
		}
	});
	
}

function showAddedScan(scanFile) {
	console.log(scanFile);
}