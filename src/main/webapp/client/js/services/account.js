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
        service.getShares = getShares;
        service.getAuthorizations = getAuthorizations;
        service.getAccount = getAccount;
        
        service.openCurrentAccount = openCurrentAccount;
        service.openSavingAccount = openSavingAccount;
        service.openJointAccount = openJointAccount;
        service.openShareAccount = openShareAccount;
        
        service.closeAccount = closeAccount;
        
        return service;
        
        function getClientAccounts(callback) {
            let url = CommonService.basePath+'clientAccounts.htm';
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
        }
        
        function getSavingBooks() {
            return CommonService.basicGetRequest('savingBooks.htm');
        }
        
        function getShares() {
            return CommonService.basicGetRequest('shares.htm');
        }
        
        function getAuthorizations() {
            return CommonService.basicGetRequest('authorizations.htm');
        }
        
        function getAccount(rib) {
            let url = CommonService.basePath+'account/'+rib+'.htm';
            let deferred = $q.defer();
            $http.get(url).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (response) {
                    deferred.reject(response.data);
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
        
        function closeAccount(data) {
            let url = CommonService.basePath+'closeAccount.htm';
            let deferred = $q.defer();
             $http.delete(url, {
                data : data,
                headers: {
                    "Content-Type": "application/json;charset=utf-8"
                }
            }).then(
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