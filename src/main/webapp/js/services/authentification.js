(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$cookies', '$rootScope', 'UserService'];
    function AuthentificationService($cookies, $rootScope, UserService) {
        var service = {};
 
        service.Login = Login;
        service.SetCredentials = SetCredentials;
        service.ClearCredentials = ClearCredentials;
 
        return service;
 
        function Login(username, password, callback) {
                var response;
                UserService.GetByName(username)
                    .then(function (user) {
                        if (user === "damien") {
                            if (password === "damien") {
                                response = { success: true };
                            } else {
                                response = { success: false, message: 'Erreur login / Mot de passe' };
                            }
                        }
                        callback(response);
                    },
                    function(err) {
                    	response = { success: false, message: err};
                    	callback(response);
                    });
            }
 
        function SetCredentials(username, password) {
            $rootScope.globals = {
                currentUser: {
                    username: username,
                    password: password
                }
            };
            console.log('currentUser: username:' + $rootScope.globals.currentUser.username + 'pass:' + $rootScope.globals.currentUser.password
                    );
            $cookies.putObject('globals', $rootScope.globals);
        }
 
        function ClearCredentials() {
            $cookies.remove('globals');
        }
    }
 
})();