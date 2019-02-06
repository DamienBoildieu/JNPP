(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['CommonService', '$http', '$q'];
    
    function UserService(CommonService, $http, $q) {
        
        let service = {};
        
        service.privateSignUp = privateSignUp;
        
        return service;      
        
        function privateSignUp(lastName, firstName, gender, birthday, email, 
            streetNbr, street, city, country, phone) {
            let data = {
                'lastName' : lastName,
                'firstName' : firstName,
                'gender' : gender,
                'birthday' : birthday,
                'email' : email,
                'streetNbr' : streetNbr,
                'street' : street,
                'city' : city,
                'country' : country,
                'phone' : phone
            };
            let url = CommonService.basePath+'privateSignUpAngular.htm';
            let deferred = $q.defer();
            $http.post(url, data).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (response) {
                    deferred.reject(response.data);
                }
            );
            return deferred.promise;
        };
    }
 
})();
 