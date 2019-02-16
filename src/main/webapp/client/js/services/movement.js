(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('MovementService', MovementService);
 
    MovementService.$inject = ['$http', '$q', 'CommonService'];
    
    function MovementService($http, $q, CommonService) {
        
        let service = {};
        
        service.debit = debit;
        service.transfert = transfert;
        service.purchase = purchase;
        
        return service;
        
        function debit(data) {
            let url = CommonService.basePath+'debit.htm';
            let deferred = $q.defer();
            $http.post(url, data).then(
                function () {
                    deferred.resolve();
                },
                function (response) {
                    deferred.reject(response.data);
                }
            );
            return deferred.promise;
        }

        function transfert(data) {
            let url = CommonService.basePath+'transfert.htm';
            let deferred = $q.defer();
            $http.post(url, data).then(
                function () {
                    deferred.resolve();
                },
                function (response) {
                    deferred.reject(response.data);
                }
            );
            return deferred.promise;
        }
        
        function purchase(data) {
            let url = CommonService.basePath+'purchase.htm';
            let deferred = $q.defer();
            $http.post(url, data).then(
                function () {
                    deferred.resolve();
                },
                function (response) {
                    deferred.reject(response.data);
                }
            );
            return deferred.promise;
        }
    }
})();