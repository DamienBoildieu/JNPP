(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('BannerController', BannerController);
    
    BannerController.$inject = ['$rootScope', '$scope', '$location', 'NotifyService',
        'AuthentificationService', 'FlashService'];
    function BannerController($rootScope, $scope, $location, NotifyService,
        AuthentificationService, FlashService) {
        let vm = this;
        
        setTemplateUrl();
        
        NotifyService.subscribe($scope, 'logInOutEvent', setTemplateUrl);
        
        vm.logout = logout;
        
        function logout() {
            AuthentificationService.logout().then(
                    function() {
                        AuthentificationService.clearCredentials();
                        vm.templateUrl = "html/unconnectedbanner.html";
                        FlashService.Success('Deconnexion reussie', true);
                        $location.path('/welcome');
                    },
                    function() {
                        FlashService.Error('Erreur lors de la deconnexion');
                    }
            );
        }
        
        function setTemplateUrl() {
            if ($rootScope.globals.userName)
        	vm.templateUrl = "html/connectedbanner.html";
            else
        	vm.templateUrl = "html/unconnectedbanner.html";
        }
    }
 
})();