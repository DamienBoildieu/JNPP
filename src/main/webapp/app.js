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
                templateUrl: 'html/common/welcome.html'
            })
            .when('/signup', {
            	controller: 'SignUpController',
                templateUrl: 'html/user/signup.html'
            })
            .when('/privatesignup', {
            	controller: 'SignUpController',
            	templateUrl: 'html/user/privatesignup.html',
            	controllerAs: 'vm'
            })
            .when('/professionalsignup', {
            	controller: 'SignUpController',
            	templateUrl: 'html/user/professionalsignup.html',
            	controllerAs: 'vm'
            })
            .when('/signupsuccess', {
                controller: 'SignUpController',
                templateUrl: 'html/user/signupsuccess.html'
            })
            .when('/connect', {
            	controller: 'ConnectController',
            	templateUrl: 'html/user/connect.html',
            	controllerAs: 'vm'
            })
            .when('/password', {
                controller: 'PasswordController',
                templateUrl: 'html/user/password.html'
            })
            .when('/privatepassword', {
            	controller: 'PasswordController',
            	templateUrl: 'html/user/privatepassword.html',
            	controllerAs: 'vm'
            })
            .when('/professionalpassword', {
                controller: 'PasswordController',
            	templateUrl: 'html/user/professionalpassword.html',
            	controllerAs: 'vm'
            })
            .when('/passwordsuccess', {
                controller: 'PasswordController',
                templateUrl: 'html/user/passwordsuccess.html'
            })
            .when('/home', {
                controller: 'HomeController',
                templateUrl: 'html/user/home.html'
            })
            .when('/userinfo', {
                controller: 'ManageUserController',
                templateUrl: 'html/user/userinfo.html'
            })
            .otherwise({ redirectTo: '/welcome' });
    }

    run.$inject = ['$rootScope', '$cookies', '$http'];
    
    function run($rootScope, $cookies, $http) {
        
        // keep user logged in after page refresh
        $rootScope.globals = $cookies.getObject('globals') || {};
        if ($rootScope.globals.userName) {
            $http.defaults.headers.common['Authorization'] = $rootScope.globals.userLogin;
        }
    }

})();