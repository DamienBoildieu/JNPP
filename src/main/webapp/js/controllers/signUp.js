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
        vm.privateSignUp = privateSignUp;
        
        function init() {
            UserService.getGenders().then(
                 function(genders) {
                    vm.genders = genders;
                 }
            );
        }
        
        function privateSignUp() {
            AuthentificationService.Login(this.username, this.password).then(
                function() {
                    AuthentificationService.SetCredentials(this.username, this.password);
                    FlashService.Success('Utilisateur connecté', true);
                    $location.path('/');
                },
                function (response) {
                    FlashService.Error(response.data, true);
                }
            );
        };
    }
 
})();

