(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ManageUserController', ManageUserController);
 
    ManageUserController.$inject = ['$rootScope', 'AuthentificationService',
        'UserService'];
    
    function ManageUserController($rootScope, AuthentificationService, UserService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
     
        init();
        
        function init() {
            let type = $rootScope.globals.userType;
            switch (type) {
                case 'PRIVATE':
                    vm.templateUrl = 'html/user/privateinfo.html';
                    break;
                case 'PROFESSIONAL':
                    vm.templateUrl = 'html/user/professionalinfo.html';
                    break;
                default:
                    break;
            }
            vm.info = UserService.getInfo();
        }
    }
})();