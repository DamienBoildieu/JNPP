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
        service.openCurrentAccount = openCurrentAccount;
        service.openSavingAccount = openSavingAccount;
        service.openJointAccount = openJointAccount;
        service.openShareAccount = openShareAccount;
        
        return service;
        
        function getClientAccounts(callback) {
            let url = CommonService.basePath+'getClientAccounts.htm';
            $http.get(url).then(
                function (response) {
                    let message = {
                        success : true,
                        message: response.data
                    };
                    callback(message);
                },
                function (response) {
                    let message = {
                        success : false,
                        message: response.data
                    };
                    callback(message);
                }
            );
            //return CommonService.basicGetRequest('getClientAccounts.htm');
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
        
        function openCurrentAccount() {
            let url = CommonService.basePath+'openCurrentAccount.htm';
            let deferred = $q.defer();
            $http.post(url).then(
                function () {
                    deferred.resolve();
                },
                function (response) {
                    deferred.reject(response.data);
                }
            );
            return deferred.promise;
        }
        
        function openSavingAccount(data) {
            let url = CommonService.basePath+'openSavingAccount.htm';
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
        
        function openJointAccount(data) {
            let url = CommonService.basePath+'openJointAccount.htm';
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
        
        function openShareAccount() {
            let url = CommonService.basePath+'openShareAccount.htm';
            let deferred = $q.defer();
            $http.post(url).then(
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