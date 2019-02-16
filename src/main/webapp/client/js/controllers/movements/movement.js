(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('MovementController', MovementController);
 
    MovementController.$inject = ['$scope', '$location', 'MovementService',
        'AuthentificationService', 'AccountService', 'FlashService'];
    
    function MovementController($scope, $location, MovementService,
        AuthentificationService, AccountService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        $scope.debitUrl = 'client/html/movements/movementdebit.html';
        $scope.transfertUrl = 'client/html/movements/movementtransfert.html';
        
        $scope.debitData = {};
        $scope.transfertData = {};
        $scope.moneyAccounts =  [];
        $scope.shareAccounts = [];
        
        vm.doDebit = doDebit;
        vm.doTransfert = doTransfert;
        
        init();
        
        function init() {
            AccountService.getClientAccounts(
                function (response) {
                    if (response.success) {
                        for (let account of response.message) {
                            if (account.type==='SHARE')
                                $scope.shareAccounts.push(account);
                            else
                                $scope.moneyAccounts.push(account);
                        }
                        setTimeout(function () {
                            $('select').formSelect();
                        }, 200);
                    } else
                       FlashService.Error(response.message); 
                }
            );
            $(document).ready(function(){
                $('.collapsible').collapsible();
            });
        }
        
        function doDebit() {
            MovementService.doDebit($scope.debitData).then(
                function() {
                    FlashService.Success('Votre virement a bien été effectué', true);
                    $location.path('/resume');
                },
                function (response) {
                    FlashService.Error(response.message); 
                }
            );
        }
        
        function doTransfert() {
            MovementService.doTransfert($scope.transfertData).then(
                function() {
                    FlashService.Success('Votre virement a bien été effectué', true);
                    $location.path('/resume');
                },
                function (response) {
                    FlashService.Error(response.message); 
                }
            );
        }
    }
})();