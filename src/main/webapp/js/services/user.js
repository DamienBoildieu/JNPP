(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['CommonService'];
    
    function UserService(CommonService) {
        
        let service = {};
        
        service.privateSignUp = privateSignUp;
        
        return service;      
        
        function privateSignUp(data) {
            return CommonService.basicRequest('privateSignUpAngular.htm', data);
        };
    }
 
})();
 