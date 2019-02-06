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
        vm.privateSignUpData = {};
        vm.privateSignUp = privateSignUp;
        
        function init() {
            CommonService.getGenders().then(
                function(genders) {
                    vm.genders = Object.getOwnPropertyNames(genders).map(k => ({key:k, value:genders[k]}));
                }
            );
        };
        
        function privateSignUp() {
            UserService.privateSignUp(vm.privateSignUpData).then(
                function(response) {
                    FlashService.Success(response, true);
                    $location.path('/');
                },
                function (response) {
                    FlashService.Error(response, true);
                }
            );
        };
    }
 
})();

