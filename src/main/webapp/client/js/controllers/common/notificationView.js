(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('NotifViewController', NotifViewController);
    
    NotifViewController.$inject = ['$scope', 'NotificationService', 
        'FlashService', 'AuthentificationService', 'TranslatorService', 'NotifyService'];
    function NotifViewController($scope, NotificationService, FlashService,
        AuthentificationService, TranslatorService, NotifyService) {
        AuthentificationService.connectedPage('/welcome');

        let vm = this;
        
        $scope.notifs = [];
        
        vm.seeAll = seeAll;
        vm.see = see;
        
        init();
                
        function init() {
            NotificationService.getNotifs().then(
                function (response) {
                    $scope.notifs = response;
                    TranslatorService.transformNotifs($scope.notifs);
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function seeAll() {
            NotificationService.seeAll().then(
                function (response) {
                    $scope.notifs = response;
                    TranslatorService.transformNotifs($scope.notifs);
                    NotifyService.notify('checkNotifsEvent');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function see(index) {
            NotificationService.see({id : $scope.notifs[index].id}).then(
                function (response) {
                    TranslatorService.transformNotif(response);
                    $scope.notifs.splice(index, 1, response);
                    NotifyService.notify('checkNotifsEvent');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
    }
 
})();