(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('NotifButtonController', NotifButtonController);
    
    NotifButtonController.$inject = ['$scope', 'NotificationService', 
        'NotifyService', 'AuthentificationService'];
    function NotifButtonController($scope, NotificationService, NotifyService,
        AuthentificationService) {
        let vm = this;
        
        $scope.isLogged = false;
        $scope.hasNotifs = false;
        
        init();
                
        function init() {
            NotifyService.subscribe($scope, 'logInOutEvent', function() {
                $scope.isLogged = AuthentificationService.isLogged;
            });
            NotifyService.subscribe($scope, 'checkNotifsEvent', function() {
                NotificationService.hasNotifs().then(
                    function (response) {
                        $scope.hasNotifs = response.hasNotifs;
                    },
                    function (response) {
                        console.log(response);
                    }
                );
            });
        }
    }
 
})();