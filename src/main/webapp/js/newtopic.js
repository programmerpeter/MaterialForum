$(document).ready(function () {
    $("#newTopicForm").validate({
        ignore: [],
        rules: {
            title: {
                required: true,
                minlength: 3
            },
            text: {
                required: true,
                minlength: 10
            }
        },
        messages: {
            title: {
                required: "Podaj tytuł!",
                minlength: "Podany tytuł jest za krótki!"
            },
            text: {
                required: "Treść tematu nie może być pusta!",
                minlength: "Wpisana treść jest za krótka!"
            }
        }
    });
});