(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('MovementController', MovementController);
 
    MovementController.$inject = ['$scope', '$location', 'MovementService', 'AuthorizationService',
        'AuthentificationService', 'AccountService', 'FlashService'];
    
    function MovementController($scope, $location, MovementService, AuthorizationService,
        AuthentificationService, AccountService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        $scope.debitUrl = 'client/html/movements/movementdebit.html';
        $scope.transfertUrl = 'client/html/movements/movementtransfert.html';
        $scope.purchaseUrl = 'client/html/movements/movementpurchase.html';
        $scope.saleUrl = 'client/html/movements/movementsale.html';
        
        $scope.debitData = {};
        $scope.transfertData = {};
        $scope.purchaseData = {};
        $scope.saleData = {};
        $scope.moneyAccounts =  [];
        $scope.shareAccounts = [];
        $scope.shares = [];
        
        vm.debit = debit;
        vm.transfert = transfert;
        vm.purchase = purchase;
        vm.sale = sale;
        
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
                        if ($scope.moneyAccounts[0]) {
                            $scope.debitData = {ribFrom : $scope.moneyAccounts[0].rib};
                            $scope.transfertData = {ribFrom : $scope.moneyAccounts[0].rib};
                        }
                        if ($scope.shareAccounts[0] && $scope.shareAccounts[0].shareTitles[0]) {
                           $scope.saleData = {share : $scope.shareAccounts[0].shareTitles[0].share.name};
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
                    if ($scope.shares[0]) {
                        $scope.purchaseData = {share : $scope.shares[0].name};     
                    }
                    setTimeout(function () {
                        $('select').formSelect();
                    }, 200);
                },
                function (response) {
                    FlashService.Error(response);
                }
            );            
            $(document).ready(function(){
                $('.collapsible').collapsible();
            });
        }
        
        function debit() {
            if ($scope.debitData.ribFrom && $scope.debitData.ribTo && $scope.debitData.amount &&
                $scope.debitData.label) {
                MovementService.debit($scope.debitData).then(
                    function() {
                        FlashService.Success('Votre virement a bien été effectué', true);
                        $location.path('/resume');
                    },
                    function (response) {
                        FlashService.Error(response); 
                    }
                );
            } else {
                FlashService.Error("Vous devez remplir tous les champs"); 
            }

        }
        
        function transfert() {
            if ($scope.transfertData.ribFrom && $scope.transfertData.ribTo && 
                $scope.transfertData.amount && $scope.transfertData.label) {
                MovementService.transfert($scope.transfertData).then(
                    function() {
                        FlashService.Success('Votre virement a bien été effectué', true);
                        $location.path('/resume');
                    },
                    function (response) {
                        FlashService.Error(response); 
                    }
                );
            } else {
                FlashService.Error("Vous devez remplir tous les champs");
            }
        }
        
        function purchase() {
            if ($scope.purchaseData.amount && $scope.purchaseData.label && 
                $scope.purchaseData.share) {
            console.log($scope.purchaseData);
                MovementService.purchase($scope.purchaseData).then(
                    function() {
                        FlashService.Success('Votre demande de titres a été acceptée', true);
                        $location.path('/resume');
                    },
                    function (response) {
                        FlashService.Error(response); 
                    }
                );
            } else {
                FlashService.Error("Vous devez remplir tous les champs");
            }
        }
        
        function sale() {
            if ($scope.saleData.amount && $scope.saleData.label && 
                $scope.saleData.share) {
                MovementService.sale($scope.saleData).then(
                    function() {
                        FlashService.Success('Votre vente de titres a été acceptée', true);
                        $location.path('/resume');
                    },
                    function (response) {
                        FlashService.Error(response); 
                    }
                );
            } else {
                FlashService.Error("Vous devez remplir tous les champs");
            }
        }
    }
})();