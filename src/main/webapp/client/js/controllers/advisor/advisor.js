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
        $scope.appoints = {};
        $scope.appointData = {};
        
        vm.makeAppoint = makeAppoint;
        vm.cancelAppoint = cancelAppoint;
        
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
        }
        
        function makeAppoint() {
            
            let date = $filter('date')($scope.appointData.date, 'dd/MM/yyyy') + ' ' +
                    $filter('date')($scope.appointData.time, 'HH:mm');
            AdvisorService.makeAppoint({date : date}).then(
                function () {
                    FlashService.Success("Votre rendez-vous a bien été pris"); 
                    AdvisorService.getAppoints().then(
                        function (response) {
                            $scope.appoints = response;
                        },
                        function (response) {
                            FlashService.Error(response);
                        }
                    );
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function cancelAppoint(index) {
            AdvisorService.cancelAppoint({id: $scope.appoints[index].id}).then(
                function () {
                    FlashService.Success("Votre rendez-vous a été supprimé");
                    AdvisorService.getAppoints().then(
                        function (response) {
                            $scope.appoints = response;
                        },
                        function (response) {
                            FlashService.Error(response);
                        }
                    );
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
    }
        
})();