(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('SignUpController', SignUpController);
 
    SignUpController.$inject = ['$scope', '$location', 'AuthentificationService', 'UserService', 'FlashService'];
    
    function SignUpController($scope, $location, AuthentificationService, UserService, FlashService) {
        AuthentificationService.unconnectedPage('/welcome');
        let vm = this;
        
        init();
        
        $scope.genders = [];
        $scope.privateSignUpData = {};
        $scope.proSignUpData = {};
        vm.privateSignUp = privateSignUp;
        vm.proSignUp = proSignUp;
        
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
        
        function privateSignUp() {
            UserService.privateSignUp($scope.privateSignUpData).then(
                function() {
                    FlashService.Success('Vous avez bien été inscrit', true);
                    $location.path('/signupsuccess');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function proSignUp() {
            UserService.proSignUp($scope.proSignUpData).then(
                function() {
                    FlashService.Success('Vous avez bien été inscrit', true);
                    $location.path('/signupsuccess');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
    }
 
})();

