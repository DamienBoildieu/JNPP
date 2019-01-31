(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ConnectController', ConnectController);
 
    ConnectController.$inject = ['$location', 'AuthentificationService', 'FlashService'];
    
    function ConnectController($location, AuthentificationService, FlashService) {
        this.connect = function() {
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
 
        initController();
        
        function initController() {
            AuthentificationService.ClearCredentials();
        }
    }
 
})();