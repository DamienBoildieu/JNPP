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
            	templateUrl: 'html/logins-view.html',
            	controllerAs: 'vm'
            })
            .when('/accounts', {
            	controller: 'AccountsController',
            	templateUrl: 'html/accounts-view.html',
            	controllerAs: 'vm'
            })
            .when('/operations', {
            	controller: 'OperationsController',
            	templateUrl: 'html/operations-view.html',
            	controllerAs: 'vm'
            })
            .when('/messages', {
            	controller: 'MessagesController',
            	templateUrl: 'html/messages-view.html',
            	controllerAs: 'vm',
                reloadOnSearch: false
            })
            .when('/advisors', {
            	controller: 'AdvisorsController',
            	templateUrl: 'html/advisors-view.html',
            	controllerAs: 'vm'
            })
            .when('/advisor', {
            	controller: 'AdvisorController',
            	templateUrl: 'html/advisor-view.html',
            	controllerAs: 'vm'
            })
            .when('/orders', {
            	controller: 'OrdersController',
            	templateUrl: 'html/orders-view.html',
            	controllerAs: 'vm'
            })
            .when('/shares', {
            	controller: 'SharesController',
            	templateUrl: 'html/shares-view.html',
            	controllerAs: 'vm'
            })
            .when('/savingbooks', {
            	controller: 'SavingbooksController',
            	templateUrl: 'html/savingbooks-view.html',
            	controllerAs: 'vm'
            })
            .when('/', {
                redirectTo: '/logins'
            })
            .otherwise({redirectTo: '/logins'});
    }

    run.$inject = [];
    function run() {}

})();