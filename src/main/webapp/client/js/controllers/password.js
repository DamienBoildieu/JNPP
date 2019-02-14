(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('PasswordController', PasswordController);
 
    PasswordController.$inject = ['$scope', '$location', 'AuthentificationService', 'UserService', 'FlashService'];
    
    function PasswordController($scope, $location, AuthentificationService, UserService, FlashService) {
        AuthentificationService.unconnectedPage('/welcome');
        let vm = this;
        
        init();
        
        $scope.privatePasswordData = {};
        $scope.proPasswordData = {};
        vm.privatePassword = privatePassword;
        vm.proPassword = proPassword;
        
        function init() {
            UserService.getGenders().then(
                function(genders) {
                    $scope.genders = genders;
                },
                function(errMsg) {
                    FlashService.Error(errMsg);
                }
            );
        }
        
        function privatePassword() {
            UserService.privatePassword($scope.privatePasswordData).then(
                function() {
                    FlashService.Success('Votre mot de passe a été réinitialisé', true);
                    $location.path('/passwordsuccess');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function proPassword() {
            UserService.proPassword($scope.proPasswordData).then(
                function() {
                    FlashService.Success('Votre mot de passe a été réinitialisé', true);
                    $location.path('/passwordsuccess');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
    }
 
})();

