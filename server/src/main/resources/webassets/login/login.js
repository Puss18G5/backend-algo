var base = base || {};
base.changeLocation = function(url) {
    window.location.replace(url);
};
base.loginController = (function() {
    var view = {
        showFailure: function(msg) {
            alert(msg);
        }
    };
    var controller = {
        view,
        load: function() {
            document.getElementById('login-form').onsubmit = function(event) {
                event.preventDefault;
                controller.loginUser();
                return false;
            };
            base.rest.getUser().then(function(user) {
                if (!user.isNone()) {
                    base.changeLocation('/');
                }
            });

            document.getElementById('create-acc').onclick = function(event) {
                var username = document.getElementById("create-usr-name").value;
                var email = document.getElementById("create-email").value;
                var password = document.getElementById("create-pass").value;

                if(controller.invalidInput(username, password, email)) {
                    alert("Invalid information is given");
                    $(".modaltoggle").attr("data-toggle", "modal");
                    $(".modaltoggle").attr("data-target", "#create-acc");
                } else {
                    var role = 'USER';
                    var credentials = {username, password, role};
                    base.rest.addUser(credentials);
                    base.rest.login(username, password, false).then(function(response){
                        if(response.ok) {
                            base.changeLocation('/');
                        } else {
                            response.json().then(error => view.showFailure(error.message));
                        }
                    });
                }
            }
        },
        loginUser: function() {
            var username = document.getElementById('username').value;
            var password = document.getElementById('password').value;
            var remember = document.getElementById('remember').checked;
            base.rest.login(username, password, remember)
                .then(function(response) {
                    if (response.ok) {
                        base.changeLocation('/');
                    } else {
                        document.getElementById('password').value = '';
                        response.json().then(error => view.showFailure(error.message));
                    }
                });
        },
        initOnLoad: function() {
            document.addEventListener('DOMContentLoaded', base.loginController.load);
        },
        validPassword: function (password) {
            var passwordRegex = /^[A-Za-z0-9\s!@#$%^&*()_+=-`~\\\]\[{}|';:/.,?><]*$/
            return password.length >= 8 && password.length <= 20 && password.match(passwordRegex);
        },
        validUsername: function (username) {
            var letterNumber = /^[0-9a-zA-Z]+$/
            return username.length >= 3 && username.length <= 20 && username.match(letterNumber);
        },
        validEmail: function (email) {
            var emailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/i
            return email.length >= 3 && email.length <= 40 && email.match(emailRegex);
        },
        invalidInput: function (username, password, email) {
            return !controller.validEmail(email) || !controller.validPassword(password) || !controller.validUsername(username);
        }
    };
    return controller;
})();
