$("#passport-series-input").click().mask("99 99");
$("#passport-number-input").click().mask("999999");

$("#lastname-input").on('input', lastname_input_handler);
$("#firstname-input").on('input', firstname_input_handler);
$("#middlename-input").on('input', middlename_input_handler);

$("#search-client-button").on('click', search_client_button_click_handler);

//обработчик ввода фамилии
function lastname_input_handler() {

    let lastname_field = $("#lastname-input");
    let input_valid = check_input(lastname_field);
    highlight_invalid_field(lastname_field, input_valid);

    change_search_client_button_state();
}

//обработчик ввода имени
function firstname_input_handler() {

    let firstname_field = $("#firstname-input");
    let input_valid = check_input(firstname_field);
    highlight_invalid_field(firstname_field, input_valid);

    change_search_client_button_state();
}

//обработчик ввода отчества
function middlename_input_handler() {

    let middlename_field = $("#middlename-input");
    let input_valid = check_input(middlename_field);
    highlight_invalid_field(middlename_field, input_valid);
}

//смена состояния кнопки поиска клиента
function change_search_client_button_state() {

    var lastname = $("#lastname-input").val();
    var firstname = $("#firstname-input").val();

    var lastname_valid = check_input($("#lastname-input"));
    var firstname_valid = check_input($("#firstname-input"));
    
    if (!!lastname && lastname.length > 0 && !!firstname && firstname.length > 0 &&
        lastname_valid && firstname_valid) {
        $("#search-client-button").removeClass('disabled');
    }
    else {
        if (!$("#search-client-button").hasClass('disabled')) {
            $("#search-client-button").addClass('disabled');
        }
    }
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

//Обработчик нажатия на кнопку поиска клиента
function search_client_button_click_handler() {

    console.log("Запущен обработчик нажатия поиска");

    let lastname_valid = check_input($("#lastname-input"));
    let firstname_valid = check_input($("#firstname-input"));
    let middlename_valid = check_input($("#middlename-input"));

    if (!lastname_valid || !firstname_valid || !middlename_valid) {

        $(function() {
            $("#search-alert-error-container").load("/static/html/search_error_alert.html");
        });  

    }
    else {
        let search_form = document.getElementById('search-client-form')
        search_form.submit();
    }
}
