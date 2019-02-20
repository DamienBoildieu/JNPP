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
            getMessages();
            let id = setInterval(getMessages, 5000);
            $scope.$on('$destroy', id);
        }
        
        function getMessages() {
            AdvisorService.getMessages().then(
                function (response) {
                    $scope.messages = response.sort(function (a,b) {
                        return new Date(a.date) - new Date(b.date);
                    });
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
                    getMessages();
                },
                function (response) {
                    FlashService.Error(response);
                }     
            );
        }
    }
        
})();