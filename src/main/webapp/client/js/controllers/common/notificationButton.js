(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('NotifButtonController', NotifButtonController);
    
    /**
     * Controleur du boutton pour acceder aux notifications
     */
    NotifButtonController.$inject = ['$scope', 'NotificationService', 
        'NotifyService', 'AuthentificationService'];
    function NotifButtonController($scope, NotificationService, NotifyService,
        AuthentificationService) {
        let vm = this;
        
        $scope.isLogged = false;
        $scope.hasNotifs = false;
        
        init();
                
        function init() {
            /*
             * Le controleur s'inscrit aux evenemnts dont il a besoin
             */
            NotifyService.subscribe($scope, 'logInOutEvent', function() {
                $scope.isLogged = AuthentificationService.isLogged;
            });
            NotifyService.subscribe($scope, 'checkNotifsEvent', function() {
                if ($scope.isLogged) {
                    NotificationService.hasNotifs().then(
                        function (response) {
                            $scope.hasNotifs = response.hasNotifs;
                        },
                        function (response) {
                            console.log(response);
                        }
                    );
                }
            });
        }
    }
 
})();