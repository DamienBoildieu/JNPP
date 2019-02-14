(function () {
    'use strict';

    angular
        .module('app', ['ngRoute'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider'];
    function config($routeProvider) {
        $routeProvider
            .when('/overview', {
                templateUrl: 'html/overview.html'
            })
            .when('/logins', {
            	controller: 'LoginsController',
            	templateUrl: 'html/logins.html',
            	controllerAs: 'vm'
            })
            .when('/accounts', {
            	controller: 'AccountsController',
            	templateUrl: 'html/accounts.html',
            	controllerAs: 'vm'
            })
            .when('/shares', {
            	controller: 'SharesController',
            	templateUrl: 'html/shares.html',
            	controllerAs: 'vm'
            })
            .when('/books', {
            	controller: 'BooksController',
            	templateUrl: 'html/books.html',
            	controllerAs: 'vm'
            })
            .otherwise({redirectTo: '/overview'});
    }

    run.$inject = [];
    function run() {}

})();