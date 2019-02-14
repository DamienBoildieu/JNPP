(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('NotifyService', NotifyService);
 
    NotifyService.$inject = ['$rootScope'];
    
    function NotifyService($rootScope) {
        let service = {};
        
        service.subscribe = subscribe;
        service.notify = notify;
        
        return service;
        
        function subscribe(scope, event, callback) {
            var handler = $rootScope.$on(event, callback);
            scope.$on('$destroy', handler);
        }
        
        function notify(event) {
            $rootScope.$emit(event);
        }
    }
})();


