(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('PasswordController', PasswordController);
 
    PasswordController.$inject = ['$location', 'AuthentificationService', 'UserService', 'FlashService'];
    
    function PasswordController($location, AuthentificationService, UserService, FlashService) {
        AuthentificationService.unconnectedPage('/welcome');
        let vm = this;
        
        init();
        
        vm.privatePasswordData = {};
        vm.proPasswordData = {};
        vm.privatePassword = privatePassword;
        vm.proPassword = proPassword;
        
        function init() {
            UserService.getGenders().then(
                function(genders) {
                    vm.genders = Object.getOwnPropertyNames(genders).map(k => ({key:k, value:genders[k]}));
                },
                function(errMsg) {
                    FlashService.Error(errMsg);
                }
            );
        }
        
        function privatePassword() {
            console.log(vm.privatePasswordData);
            UserService.privatePassword(vm.privatePasswordData).then(
                function(response) {
                    FlashService.Success(response, true);
                    $location.path('/passwordsuccess');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function proPassword() {
            UserService.proPassword(vm.proPasswordData).then(
                function(response) {
                    FlashService.Success(response, true);
                    $location.path('/passwordsuccess');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
    }
 
})();

