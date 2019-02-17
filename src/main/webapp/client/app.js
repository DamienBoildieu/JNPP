(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngCookies'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider) {
        $routeProvider
            .when('/welcome', {
                templateUrl: 'client/html/common/welcome.html'
            })
            .when('/signup', {
            	controller: 'SignUpController',
                templateUrl: 'client/html/user/signup.html'
            })
            .when('/privatesignup', {
            	controller: 'SignUpController',
            	templateUrl: 'client/html/user/privatesignup.html',
            	controllerAs: 'vm'
            })
            .when('/professionalsignup', {
            	controller: 'SignUpController',
            	templateUrl: 'client/html/user/professionalsignup.html',
            	controllerAs: 'vm'
            })
            .when('/signupsuccess', {
                controller: 'SignUpController',
                templateUrl: 'client/html/user/signupsuccess.html'
            })
            .when('/connect', {
            	controller: 'ConnectController',
            	templateUrl: 'client/html/user/connect.html',
            	controllerAs: 'vm'
            })
            .when('/password', {
                controller: 'PasswordController',
                templateUrl: 'client/html/user/password.html'
            })
            .when('/privatepassword', {
            	controller: 'PasswordController',
            	templateUrl: 'client/html/user/privatepassword.html',
            	controllerAs: 'vm'
            })
            .when('/professionalpassword', {
                controller: 'PasswordController',
            	templateUrl: 'client/html/user/professionalpassword.html',
            	controllerAs: 'vm'
            })
            .when('/passwordsuccess', {
                controller: 'PasswordController',
                templateUrl: 'client/html/user/passwordsuccess.html'
            })
            .when('/home', {
                controller: 'HomeController',
                templateUrl: 'client/html/user/home.html'
            })
            .when('/userinfo', {
                controller: 'ManageUserController',
                templateUrl: 'client/html/user/userinfo.html',
                controllerAs: 'vm'
            })
            .when('/resume', {
                controller: 'ResumeController',
                templateUrl: 'client/html/accounts/resume.html',
                controllerAs: 'vm'
            })
            .when('/openaccount', {
                controller: 'OpenAccountController',
                templateUrl: 'client/html/accounts/openaccount.html',
                controllerAs: 'vm'
            })
            .when('/account/:accountRib', {
                controller: 'AccountController',
                templateUrl: 'client/html/accounts/account.html',
                controllerAs: 'vm'
            })
            .when('/movement', {
                controller: 'MovementController',
                templateUrl: 'client/html/movements/movement.html',
                controllerAs: 'vm'
            })
            .otherwise({ redirectTo: '/welcome' });
    }

    run.$inject = ['$cookies', 'AuthentificationService'];
    
    function run($cookies, AuthentificationService) {
        
        // keep user logged in after page refresh
        let authorization = $cookies.getObject('authorization');
        if (authorization) {
            AuthentificationService.reconnect(authorization);
        }
    }

})();