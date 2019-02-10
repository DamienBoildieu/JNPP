(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('SignUpController', SignUpController);
 
    SignUpController.$inject = ['$location', 'UserService', 'FlashService'];
    
    function SignUpController($location, UserService, FlashService) {
        let vm = this;
        
        init();
        
        vm.genders = [];
        vm.privateSignUpData = {};
        vm.proSignUpData = {};
        vm.privateSignUp = privateSignUp;
        vm.proSignUp = proSignUp;
        
        function init() {
            UserService.getGenders().then(
                function(genders) {
                    vm.genders = Object.getOwnPropertyNames(genders).map(k => ({key:k, value:genders[k]}));
                },
                function(errMsg) {
                    FlashService.Error(errMsg);
                }
            );
        };
        
        function privateSignUp() {
            UserService.privateSignUp(vm.privateSignUpData).then(
                function(response) {
                    FlashService.Success(response, true);
                    $location.path('/signupsuccess');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        };
        
        function proSignUp() {
            UserService.proSignUp(vm.proSignUpData).then(
                function(response) {
                    FlashService.Success(response, true);
                    $location.path('/signupsuccess');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        };
    }
 
})();

