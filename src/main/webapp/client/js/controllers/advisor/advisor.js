(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AdvisorController', AdvisorController);
 
    AdvisorController.$inject = ['$scope', '$filter', 'AdvisorService',
        'AuthentificationService', 'FlashService'];
    
    function AdvisorController($scope, $filter, AdvisorService,
        AuthentificationService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        
        $scope.advisor = {};
        $scope.appoints = [];
        $scope.messages = [];
        
        $scope.appointData = {};
        $scope.messageData = {};
        
        vm.makeAppoint = makeAppoint;
        vm.cancelAppoint = cancelAppoint;
        vm.sendMessage = sendMessage;
        
        init();
        
        function init() {
            AdvisorService.getAdvisor().then(
                function (response) {
                    $scope.advisor = response;
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
            AdvisorService.getAppoints().then(
                function (response) {
                    $scope.appoints = response;
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
            AdvisorService.getMessages().then(
                function (response) {
                    $scope.messages = response;
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function makeAppoint() {
            
            let date = $filter('date')($scope.appointData.date, 'dd/MM/yyyy') + ' ' +
                    $filter('date')($scope.appointData.time, 'HH:mm');
            AdvisorService.makeAppoint({date : date}).then(
                function (response) {
                    $scope.appoints.push(response);
                    FlashService.Success("Votre rendez-vous a bien été pris"); 
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function cancelAppoint(index) {
            AdvisorService.cancelAppoint({id: $scope.appoints[index].id}).then(
                function () {
                    $scope.appoints.splice(index, 1),
                    FlashService.Success("Votre rendez-vous a été supprimé");
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function sendMessage() {
            AdvisorService.sendMessage($scope.messageData).then(
               function (response) {
                   $scope.messages.push(response);
                    FlashService.Success("Votre a été envoyé");
                },
                function (response) {
                    FlashService.Error(response);
                }     
            );
        }
    }
        
})();