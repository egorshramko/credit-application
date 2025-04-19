export function addFileBtnHandler() {
    console.log('Тыкнули на добавление файла');

    let scansControl = document.getElementById('scans-control');

    scansControl.click();
}

export function selectFileHandler(event) {
	
	console.log(event.target);
	let files = event.target.files;
	
	if (!!files && files.length > 0) {
		
		//подгружаем последний загруженный файл
		let reader = new FileReader();
		reader.readAsDataURL(files[0]);
		
		reader.onload = () => {
			let file = reader.result;
			showAddedScan(file);
		}
		
		reader.onerror = () => {
			console.error(reader.error);
		}
		
	}
	
	
	
}

function showAddedScan(scanFile) {
	console.log(scanFile);
}