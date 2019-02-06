(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('SignUpController', SignUpController);
 
    SignUpController.$inject = ['$location', 'CommonService', 'UserService', 'FlashService'];
    
    function SignUpController($location, CommonService, UserService, FlashService) {
        let vm = this;
        
        init();
        
        vm.genders = [];
        vm.privateSignUp = privateSignUp;
        
        function init() {
            CommonService.getGenders().then(
                function(genders) {
                    vm.genders = Object.getOwnPropertyNames(genders).map(k => ({key:k, value:genders[k]}));
                }
            );
        };
        
        function privateSignUp() {
            UserService.privateSignUp(vm.lastName, vm.firstName, vm.gender,
                vm.birthday, vm.email, vm.streetNbr, vm.street, vm.city,
                vm.country, vm.phone).then(
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

