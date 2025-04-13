import * as widget_photo_camera from './widget.photo/widget.photo.camera.js';
import * as widget_photo_file from './widget.photo/widget.photo.file.js';

$("#camera-photo-btn").on('click', widget_photo_camera.camera_photo_btn_handler);
$("#camera-popup").on('hide.bs.modal', function () {
    $(document.activeElement).blur();
});

$("#clear-photo-btn").on('click', widget_photo_camera.clear_photo);
$("#photo-load-widget-container").on('mouseover', widget_photo_camera.show_widget_controls);
$("#photo-load-widget-container").on('mouseout', widget_photo_camera.hide_widget_controls);

$("#file-photo-btn").on('click', widget_photo_file.file_btn_click_handler);
$("#file-photo-input").on('change', widget_photo_file.file_uploaded);