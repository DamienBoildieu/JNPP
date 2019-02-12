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
                    vm.genders = genders;
                },
                function(errMsg) {
                    FlashService.Error(errMsg);
                }
            );
        }
        
        function privatePassword() {
            UserService.privatePassword(vm.privatePasswordData).then(
                function() {
                    FlashService.Success('Vous avez bien ete inscrit', true);
                    $location.path('/passwordsuccess');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function proPassword() {
            UserService.proPassword(vm.proPasswordData).then(
                function() {
                    FlashService.Success('Vous avez bien ete inscrit', true);
                    $location.path('/passwordsuccess');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
    }
 
})();

