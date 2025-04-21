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
			showAddedScan(data, lastUploadedFile.name);
		}
	});
	
}

/*<div th:each="scan : *{passport.scans}" 
    th:id="${'passport-scan-' + scan.id}" 
    th:value="${scan.id}"
    class="col-12 form-control d-flex justify-content-between scan-element"
>

    <input th:id="${'scan-control-' + scan.id}" th:files="${scan.scanFile.content}" class="d-none" />
    <div class="d-flex flex-column justify-content-center">
        <span class="p-0" th:text="${scan.scanFile.name}">File name</span>
    </div>
    <div class="d-flex">
        <button type="button" class="btn btn-sm btn-outline-secondary mx-1">
            <span class="bi bi-download"></span>
        </button>
        <button type="button" class="btn btn-sm btn-outline-danger">
            <span class="bi bi-trash3"></span>
        </button>
    </div>

</div>*/

function showAddedScan(scanFileUUID, fileName) {
	let scansCollectionContainer = document.getElementById('scans-collection-container');
	
	let scanContainer = document.createElement('div');
	scanContainer.classList.add('col-12', 'form-control', 'd-flex', 'justify-content-between', 'scan-element');
	
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
		console.log("Кликнули на скачивание файла");
	});
	
	let removeButton = document.createElement('button');
	removeButton.setAttribute('type', 'button');
	removeButton.setAttribute('for', scanContainerId);
	removeButton.classList.add('btn', 'btn-sm', 'btn-outline-danger');
	
	let removeButtonSpan = document.createElement('span');
	removeButtonSpan.classList.add('bi', 'bi-trash3');
	
	removeButton.appendChild(removeButtonSpan);
	removeButton.addEventListener('click', () => {
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
}