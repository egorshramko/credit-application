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
		url: window.location.pathname + '/scan',
		data: data,
		cache: false, 
		contentType: false,
		dataType: 'json', 
		processData: false, 
		method: 'POST',
		success: (data) => {
			showAddedScan(data.id, lastUploadedFile.name);
		}
	});
	
}

function showAddedScan(scanFileUUID, fileName) {
	let scansCollectionContainer = document.getElementById('scans-collection-container');
	
	let scanContainer = document.createElement('div');
	scanContainer.classList.add('col-12', 'form-control', 'd-flex', 'justify-content-between', 'scan-element', 'mb-2');
	
	let scansContainerNumber = scansCollectionContainer.childNodes.length;
	let scanContainerId = 'scan-control-' + String(scansContainerNumber);
	
	while(!!document.getElementById(scanContainerId)) {
		scansContainerNumber++;
		scanContainerId = 'scan-control-' + String(scansContainerNumber);
	}
	scanContainer.setAttribute('id', scanContainerId);
	scanContainer.setAttribute('value', scanFileUUID);
	
	let nameContainer = document.createElement('div');
	nameContainer.classList.add('d-flex', 'flex-column', 'justify-content-center');
	
	let nameSpan = document.createElement('span');
	nameSpan.classList.add('p-0');
	nameSpan.innerText = fileName;
	
	nameContainer.appendChild(nameSpan);
	
	let buttonsContainer = document.createElement('div');
	buttonsContainer.classList.add('d-flex');
	
	let downloadButton = document.createElement('button');
	downloadButton.setAttribute('type', 'button');
	downloadButton.setAttribute('for', scanContainerId);
	downloadButton.classList.add('btn', 'btn-sm', 'btn-outline-secondary', 'mx-1');
	
	let downloadButtonSpan = document.createElement('span');
	downloadButtonSpan.classList.add('bi', 'bi-download');
	
	downloadButton.appendChild(downloadButtonSpan);
	downloadButton.addEventListener('click', () => {
		downloadButton.blur();
		downloadScan(downloadButton.getAttribute('for'));
	});
	
	let removeButton = document.createElement('button');
	removeButton.setAttribute('type', 'button');
	removeButton.setAttribute('for', scanContainerId);
	removeButton.classList.add('btn', 'btn-sm', 'btn-outline-danger');
	
	let removeButtonSpan = document.createElement('span');
	removeButtonSpan.classList.add('bi', 'bi-trash3');
	
	removeButton.appendChild(removeButtonSpan);
	removeButton.addEventListener('click', () => {
		removeButton.blur();
		removeScan(removeButton.getAttribute('for'));
	});
	
	buttonsContainer.appendChild(downloadButton);
	buttonsContainer.appendChild(removeButton);
	
	scanContainer.appendChild(nameContainer);
	scanContainer.appendChild(buttonsContainer);
	
	scansCollectionContainer.appendChild(scanContainer);
	
}

function removeScan(controlId) {
	console.log("Кликнули на удаление файла");
	console.log("controlId: " + controlId);
	
	let scanControl = document.getElementById(controlId);
	let storageUUID = scanControl.getAttribute('value');
	
	$.ajax({
		url: window.location.pathname + '/scan/' + storageUUID,
		method: 'DELETE',
		dataType: 'json',
		success: (data) => {
			if (data.removed) {
				removeFileFromCollection(controlId);
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

function removeFileFromCollection(controlId) {
	let scanControl = document.getElementById(controlId);
	if (!!scanControl) {
		scanControl.remove();
	}
}

function downloadFile(url, filename) {
	const a = document.createElement('a');
	a.href = url;
	a.download = filename;
	a.style.display = 'none';
	document.body.appendChild(a);
	a.click();
	document.body.removeChild(a);
}

async function downloadScan(controlId) {
	console.log("Кликнули на скачивание файла");
	console.log("controlId: " + controlId);
	
	let scanControl = document.getElementById(controlId);
	let storageUUID = scanControl.getAttribute('value');
	let filename;
	
	try {
		const response = await fetch('/storage/download/' + storageUUID)
				.then((response) => {
					if (!response.ok) throw new Error("Download response was not ok");
					filename = response.headers.get('Content-Disposition')
						.replace('attachment; filename="', '')
						.replace('"', '');
						
					console.log("filename: " + filename);
					
					return response.blob();
				})
				.then((blob) => {
					const url = window.URL.createObjectURL(blob);
					downloadFile(url, filename);
					window.URL.revokeObjectURL(url);
					
				});
	}
	catch (error) {
		console.error(error);
	}
	
}