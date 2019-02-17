(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('NotificationService', NotificationService);
 
    NotificationService.$inject = ['$http', '$q', 'CommonService'];
    
    function NotificationService($http, $q, CommonService) {
        
        let service = {};
        
        service.hasNotifs = hasNotifs;
        service.getNotifs = getNotifs;
        service.see = see;
        service.seeAll = seeAll;

        return service;
        
        function hasNotifs() {
            return CommonService.basicGetRequest('hasNotifs.htm');
        }

        function getNotifs() {
            return CommonService.basicGetRequest('notifs.htm');
        }
        
        function see(data) {
            return CommonService.basicPutRequest('seeNotif.htm', data);
        }
        
        function seeAll() {
            let url = CommonService.basePath+'seeAllNotifs.htm';
            let deferred = $q.defer();
            $http.put(url).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (response) {
                    deferred.reject(response.data);
                }
            );
            return deferred.promise;
        }
    }
})();