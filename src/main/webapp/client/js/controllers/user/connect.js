(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ConnectController', ConnectController);
 
    ConnectController.$inject = ['$scope', '$location', 'NotifyService', 
        'AuthentificationService', 'FlashService'];
    
    function ConnectController($scope, $location, NotifyService, AuthentificationService, FlashService) {
        AuthentificationService.unconnectedPage('/welcome');
        let vm = this;
        
        $scope.connectData = {};
        vm.connect = connect;
        
        function connect() {
            AuthentificationService.login($scope.connectData, function(response) {
                if (response.success) {
                    NotifyService.notify('logInOutEvent');
                    FlashService.Success('Utilisateur connecté', true);
                    $location.path('/home');
                } else {
                    FlashService.Error(response.message);
                }
            });
        }
    }
 
})();