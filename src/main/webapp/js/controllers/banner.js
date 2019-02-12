(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('BannerController', BannerController);
    
    BannerController.$inject = ['$scope', '$location', 'NotifyService', 
        'AuthentificationService', 'FlashService'];
    function BannerController($scope, $location, NotifyService,
        AuthentificationService, FlashService) {
        let vm = this;
        
        setTemplateUrl();
        
        NotifyService.subscribe($scope, 'logInOutEvent', setTemplateUrl);
        
        vm.logout = logout;
        
        function logout() {
            AuthentificationService.logout();
            vm.templateUrl = "html/common/unconnectedbanner.html";
            FlashService.Success('Deconnexion reussie', true);
            $location.path('/welcome');
        }
        
        function setTemplateUrl() {
            if (AuthentificationService.isLogged)
        	vm.templateUrl = "html/common/connectedbanner.html";
            else
        	vm.templateUrl = "html/common/unconnectedbanner.html";
        }
    }
 
})();