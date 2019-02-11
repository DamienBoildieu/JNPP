(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngCookies'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
        $routeProvider
            .when('/welcome', {
                templateUrl: 'html/common/welcome.html'
            })
            .when('/signup', {
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
                templateUrl: 'html/user/signupsuccess.html'
            })
            .when('/connect', {
            	controller: 'ConnectController',
            	templateUrl: 'html/user/connect.html',
            	controllerAs: 'vm'
            })
            .when('/password', {
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
            .when('/home', {
                templateUrl: 'html/user/home.html'
            })
            .otherwise({ redirectTo: '/welcome' });
    }

    run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
    
    function run($rootScope, $location, $cookies, $http) {
        
        // keep user logged in after page refresh
        $rootScope.globals = $cookies.getObject('globals') || {};
        if ($rootScope.globals.userName) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.userName.authdata;
        }
        
       /* $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            let connectedOnly = ['/signup', 
                '/privatesignup', '/professionalsignup', '/signupsuccess', '/connect',
                '/privatepassword', '/professionalpassword'];
            let connectedPage = $.inArray($location.path(), 
                connectedOnly.concat(['', '/', '/welcome'])) === -1;
            let notConnectedPage =  $.inArray($location.path(), connectedOnly) !== -1;
            let loggedIn = $rootScope.globals.userName;
            if (connectedPage && !loggedIn) {
                $location.path('/connect');
            } else if (notConnectedPage && loggedIn) {
            	$location.path('/welcome');
            }
        });*/
    }

})();