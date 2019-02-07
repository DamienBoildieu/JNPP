(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['CommonService'];
    
    function UserService(CommonService) {
        
        let service = {};
        
        service.privateSignUp = privateSignUp;
        service.proSignUp = proSignUp;
        
        return service;      
        
        function privateSignUp(data) {
            return CommonService.basicRequest('privateSignUpAngular.htm', data);
        };
        
        function proSignUp(data) {
            return CommonService.basicRequest('proSignUpAngular.htm', data);
        };
    }
 
})();
 