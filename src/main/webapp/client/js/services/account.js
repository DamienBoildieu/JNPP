(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AccountService', AccountService);
 
    AccountService.$inject = ['$http', '$q', 'CommonService'];
    
    function AccountService($http, $q, CommonService) {
        
        let service = {};
        
        service.getClientAccounts = getClientAccounts;
        service.getSavingBooks = getSavingBooks;
        
        return service;
        
        function getClientAccounts() {
            return CommonService.basicGetRequest('getClientAccounts.htm');
        }
        
        function getSavingBooks() {
            let url = CommonService.basePath+'getSavingBooks.htm';
            let deferred = $q.defer();
            $http.get(url).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function () {
                    deferred.reject("Erreur rencontrée dans le serveur");
                }
            );
            return deferred.promise;
        }
    }
})();