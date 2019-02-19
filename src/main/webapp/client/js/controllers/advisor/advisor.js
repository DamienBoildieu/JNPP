(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AdvisorController', AdvisorController);
 
    /**
     * Controleur des vue du conseiller et des rendez-vous
     */
    AdvisorController.$inject = ['$scope', '$filter', 'AdvisorService',
        'AuthentificationService', 'FlashService'];   
    function AdvisorController($scope, $filter, AdvisorService,
        AuthentificationService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        
        $scope.advisor = {};
        $scope.appoints = [];
        
        $scope.appointData = {};
        
        vm.makeAppoint = makeAppoint;
        vm.cancelAppoint = cancelAppoint;
        
        init();
        
        /**
         * Recupere toutes les informations du conseiller, les rendez-vous et les messages
         */
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
        }
        
        function makeAppoint() {
            //On met en forme la date
            let date = $filter('date')($scope.appointData.date, 'dd/MM/yyyy') + ' ' +
                    $filter('date')($scope.appointData.time, 'HH:mm');
            AdvisorService.makeAppoint({date : date}).then(
                function (response) {
                    $scope.appoints.push(response);
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function cancelAppoint(index) {
            AdvisorService.cancelAppoint({id: $scope.appoints[index].id}).then(
                function () {
                    $scope.appoints.splice(index, 1);
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
    }
        
})();