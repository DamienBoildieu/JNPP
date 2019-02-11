(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ConnectController', ConnectController);
 
    ConnectController.$inject = ['$location', 'NotifyService', 
        'AuthentificationService', 'FlashService'];
    
    function ConnectController($location, NotifyService,
        AuthentificationService, FlashService) {
        let vm = this;
        
        vm.connectData = {};
        vm.connect = connect;
        
        function connect() {
            AuthentificationService.login(vm.connectData).then(
                function(response) {
                    console.log(response);
                    AuthentificationService.setCredentials(response);
                    NotifyService.notify('logInOutEvent');
                    FlashService.Success('Utilisateur connecté', true);
                    $location.path('/home');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        };
    }
 
})();