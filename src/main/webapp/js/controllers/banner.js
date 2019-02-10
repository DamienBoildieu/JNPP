(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('BannerController', BannerController);
    
    BannerController.$inject = ['$rootScope', '$location', 'AuthentificationService', 'FlashService'];
    function BannerController($rootScope, $location, AuthentificationService, FlashService) {
        let vm = this;
        let loggedIn = $rootScope.globals.userName;
        if (loggedIn)
        	vm.templateUrl = "html/connectedbanner.html";
        else
        	vm.templateUrl = "html/unconnectedbanner.html";
        vm.logout = logout;
        
        function logout() {
            AuthentificationService.logout(
                function(response) {
                    if (response.success) {
                        AuthentificationService.clearCredentials();
                        FlashService.Success('Deconnexion reussie', true);
                        $location.path('/welcome');
                    } else {
                        FlashService.Error('Erreur lors de la deconnexion');
                    }
                }
            );
        }
    }
 
})();