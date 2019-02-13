(function () {
    'use strict';

    angular
        .module('app-banker', ['ngRoute'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider'];
    function config($routeProvider) {
        $routeProvider
            .when('/overview', {
                templateUrl: 'html/banker/overview.html'
            })
            .otherwise({redirectTo: '/overview'});
    }

    run.$inject = [];
    function run() {}

})();