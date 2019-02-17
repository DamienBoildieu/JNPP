(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AdvisorService', AdvisorService);
 
    AdvisorService.$inject = ['$http', '$q', 'CommonService'];
    
    function AdvisorService($http, $q, CommonService) {
        
        let service = {};
        
        service.getAdvisor = getAdvisor;
        service.getAppoints = getAppoints;
        service.getMessages = getMessages;
        service.makeAppoint = makeAppoint;
        service.cancelAppoint = cancelAppoint;
        service.sendMessage = sendMessage;
        
        return service;
        
        
        function getAdvisor() {
            return CommonService.basicGetRequest('clientAdvisor.htm');
        }
        
        function getAppoints() {
            return CommonService.basicGetRequest('clientAppoints.htm');
        }
        
        function getMessages() {
            return CommonService.basicGetRequest('clientMessages.htm');
        }
        
        function makeAppoint(data) {
            let url = CommonService.basePath+'makeAppoint.htm';
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
        
        function cancelAppoint(data) {
            let url = CommonService.basePath+'cancelAppoint.htm';
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
        
        function sendMessage(data) {
            let url = CommonService.basePath+'sendMessage.htm';
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