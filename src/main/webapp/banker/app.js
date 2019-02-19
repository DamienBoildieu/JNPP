(function () {
    'use strict';

    angular
        .module('app', ['ngRoute'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider'];
    function config($routeProvider) {
        $routeProvider
            .when('/logins', {
            	controller: 'LoginsController',
            	templateUrl: 'html/logins.html',
            	controllerAs: 'vm'
            })
            .when('/messages', {
            	controller: 'MessagesController',
            	templateUrl: 'html/messages.html',
            	controllerAs: 'vm',
                reloadOnSearch: false
            })
            .when('/accounts', {
            	controller: 'AccountsController',
            	templateUrl: 'html/accounts.html',
            	controllerAs: 'vm'
            })
            .when('/advisors', {
            	controller: 'AdvisorsController',
            	templateUrl: 'html/advisors.html',
            	controllerAs: 'vm'
            })
            .when('/advisor', {
            	controller: 'AdvisorController',
            	templateUrl: 'html/advisor.html',
            	controllerAs: 'vm'
            })
            .when('/purchases', {
            	controller: 'PurchasesController',
            	templateUrl: 'html/purchases.html',
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
            .otherwise({redirectTo: '/logins'});
    }

    run.$inject = [];
    function run() {}

})();