(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngCookies'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
        $routeProvider
            .when('/home', {
                templateUrl: 'html/home.html'
            })
            .when('/signup', {
                controller: 'SignUpController',
                templateUrl: 'html/signup.html',
                controllerAs: 'vm'
            })
            .when('/connect', {
            	controller: 'ConnectController',
            	templateUrl: 'html/connect.html',
            	controllerAs: 'vm'
            })
            .otherwise({ redirectTo: '/home' });
  
    }

    run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
    
    function run($rootScope, $location, $cookies, $http) {
        
        // keep user logged in after page refresh
        $rootScope.globals = $cookies.getObject('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            var connectedPage = $.inArray($location.path(), ['/home', '/signup', '/connect']) === -1;
            var notConnectedPage =  $.inArray($location.path(), ['/signup', '/connect']) !== -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (connectedPage && !loggedIn) {
                $location.path('/connect');
            } else if (notConnectedPage && loggedIn) {
            	$location.path('/home');
            }
        });
    }

})();