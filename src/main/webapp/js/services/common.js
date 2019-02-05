(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('CommonService', CommonService);
 
    CommonService.$inject = ['$http', '$q'];
    
    function CommonService($http, $q) {
        
        let service = {};
        
        service.basePath = 'http://localhost:8080/JNPP/';
        
        return service;
    }
 
})();
