(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('SignUpController', SignUpController);
 
    SignUpController.$inject = ['$location', 'AuthentificationService', 'UserService', 'FlashService'];
    
    function SignUpController($location, AuthentificationService, UserService, FlashService) {
        AuthentificationService.unconnectedPage('/welcome');
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
                    vm.genders = genders;
                },
                function(errMsg) {
                    FlashService.Error(errMsg);
                }
            );
        }
        
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
        }
        
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
        }
    }
 
})();

