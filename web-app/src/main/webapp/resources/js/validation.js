$(document).ready(function () {

    $("#registrationform").validate({

        rules: {
            email: {
                required: true,
                email: true,
                minlength: 4,
                maxlength: 30
            },
            password: {
                required: true,
                minlength: 4,
                maxlength: 16
            },
            first_name: {
                required: true,
                lettersonly: true
            },
            last_name: {
                required: true,
                lettersonly: true
            },
            repeatPassword: {
                equalTo: "#password"
            }
        },

        messages: {
            repeatPassword: {
                equalTo: "Passwords are not equals"
            }
        }
    });

    $("#loginform").validate({

        rules: {
            email: {
                required: true,
                email: true,
                minlength: 8,
                maxlength: 30
            },
            password: {
                required: true,
                minlength: 4,
                maxlength: 16
            }
        }
    });


    $("#updateform").validate({

        rules: {
            email: {
                email: true,
                minlength: 8,
                maxlength: 30
            },
            password: {
                minlength: 4,
                maxlength: 16
            },
            first_name: {
                lettersonly: true,
                minlength: 2,
                maxlength: 35
            },
            last_name: {
                lettersonly: true,
                minlength: 2,
                maxlength: 35
            },
            icq: {
                minlength: 5,
                maxlength: 10
            },
            skype: {
                minlength: 5,
                maxlength: 35
            }
        }
    });
});