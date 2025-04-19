import * as addFile from './components/add.file.js';

$("#add-scan-button").on('click', addFile.addFileBtnHandler);
$("#scans-control").on('change', addFile.selectFileHandler);
