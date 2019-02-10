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
                templateUrl: 'html/welcome.html'
            })
            .when('/signup', {
                templateUrl: 'html/signup.html'
            })
            .when('/privatesignup', {
            	controller: 'SignUpController',
            	templateUrl: 'html/privatesignup.html',
            	controllerAs: 'vm'
            })
            .when('/professionalsignup', {
            	controller: 'SignUpController',
            	templateUrl: 'html/professionalsignup.html',
            	controllerAs: 'vm'
            })
            .when('/signupsuccess', {
                templateUrl: 'html/signupsuccess.html'
            })
            .when('/connect', {
            	controller: 'ConnectController',
            	templateUrl: 'html/connect.html',
            	controllerAs: 'vm'
            })
            .when('/home', {
                templateUrl: 'html/home.html'
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

        $rootScope.basePath = 'http://localhost:8080/JNPP/';
        
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            let connectedPage = $.inArray($location.path(), 
                ['', '/', '/welcome', '/signup', '/privatesignup',
                '/professionalsignup', '/signupsuccess', '/connect']) === -1;
            let notConnectedPage =  $.inArray($location.path(), ['/signup', 
                '/privatesignup', '/professionalsignup', '/signupsuccess', '/connect']) !== -1;
            let loggedIn = $rootScope.globals.userName;
            if (connectedPage && !loggedIn) {
                $location.path('/connect');
            } else if (notConnectedPage && loggedIn) {
            	$location.path('/welcome');
            }
        });
    }

})();