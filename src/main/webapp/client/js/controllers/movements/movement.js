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
        $scope.purchaseUrl = 'client/html/movements/movementpurchase.html';
        
        $scope.debitData = {};
        $scope.transfertData = {};
        $scope.purchaseData = {};
        $scope.moneyAccounts =  [];
        $scope.shareAccounts = [];
        
        vm.debit = debit;
        vm.transfert = transfert;
        vm.purchase = purchase;
        
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
            AccountService.getShares().then(
                function (response) {
                    $scope.shares = response;
                    setTimeout(function () {
                        $('select').formSelect();
                    }, 200);
                }
            );
            $(document).ready(function(){
                $('.collapsible').collapsible();
            });
        }
        
        function debit() {
            MovementService.debit($scope.debitData).then(
                function() {
                    FlashService.Success('Votre virement a bien été effectué', true);
                    $location.path('/resume');
                },
                function (response) {
                    FlashService.Error(response.message); 
                }
            );
        }
        
        function transfert() {
            MovementService.transfert($scope.transfertData).then(
                function() {
                    FlashService.Success('Votre virement a bien été effectué', true);
                    $location.path('/resume');
                },
                function (response) {
                    FlashService.Error(response.message); 
                }
            );
        }
        
        function purchase() {
            MovementService.purchase($scope.purchaseData).then(
                function() {
                    FlashService.Success('Votre demande de titres a été acceptée', true);
                    $location.path('/resume');
                },
                function (response) {
                    FlashService.Error(response.message); 
                }
            );
        }  
    }
})();