(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('MovementService', MovementService);
 
    MovementService.$inject = ['$http', '$q', 'CommonService'];
    
    function MovementService($http, $q, CommonService) {
        
        let service = {};
        
        service.doDebit = doDebit;
        
        return service;
        
        function doDebit(data) {
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
    }
})();