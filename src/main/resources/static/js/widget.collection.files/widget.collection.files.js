import * as addFile from './components/add.file.js';

$("#add-scan-button").on('click', addFile.addFileBtnHandler);
$("#scans-control").on('change', addFile.selectFileHandler);

let downloadButtons = document.querySelectorAll('.scan-download-btn');
downloadButtons.forEach((button) => {
	button.addEventListener('click', () => {
		button.blur();
		addFile.downloadScan(button.getAttribute('for'));
	});
});

let removeButtons = document.querySelectorAll('.scan-remove-btn');
removeButtons.forEach((button) => {
	button.addEventListener('click', () => {
		button.blur();
		addFile.removeScan(button.getAttribute('for'));
	});
});
