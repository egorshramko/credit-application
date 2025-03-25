$("#passport-series-input").click().mask("99 99");
$("#passport-number-input").click().mask("999999");

$("#lastname-input").on('input', change_search_client_button_state);
$("#firstname-input").on('input', change_search_client_button_state);
$("#middlename-input").on('input', '#middlename-input', check_input);

//смена состояния кнопки поиска клиента
function change_search_client_button_state() {

    var lastname = $("#lastname-input").val();
    var firstname = $("#firstname-input").val();

    var lastname_valid = check_input($("#lastname-input"));
    var firstname_valid = check_input($("#firstname-input"));
    
    if (!!lastname && lastname.length > 0 && !!firstname && firstname.length > 0) {
        if (lastname_valid && firstname_valid) {
            $("#search-client-button").removeClass('disabled');
        }
    }
    else {
        if (!$("#search-client-button").hasClass('disabled')) {
            $("#search-client-button").addClass('disabled');
        }
    }
}

//проверка корректности ввода
function check_input(element) {
    const regex = /[0-9]+/;
    var input_value = element.val();

    var value_valid = regex.test(input_value);

    if (!value_valid) {
        if (!element.hasClass('is-invalid')) {
            element.addClass('is-invalid');
        }
    }
    else {
        if (element.hasClass('is-invalid')) {
            element.removeClass('is-invalid');
        }
    }

    return value_valid;

}

