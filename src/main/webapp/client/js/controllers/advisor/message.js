(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('MessageController', MessageController);
 
    /**
     * Controleur des messages
     */
    MessageController.$inject = ['$scope', 'AdvisorService',
        'AuthentificationService', 'FlashService'];   
    function MessageController($scope, AdvisorService,
        AuthentificationService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        
        $scope.messages = [];
        
        $scope.messageData = {};
        
        vm.sendMessage = sendMessage;
        
        init();
        
        /**
         * Recupere  les messages
         */
        function init() {
            AdvisorService.getMessages().then(
                function (response) {
                    $scope.messages = response;
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function sendMessage() {
            AdvisorService.sendMessage($scope.messageData).then(
               function (response) {
                    $scope.messageData = {};
                    $scope.messages.push(response);
                },
                function (response) {
                    FlashService.Error(response);
                }     
            );
        }
    }
        
})();