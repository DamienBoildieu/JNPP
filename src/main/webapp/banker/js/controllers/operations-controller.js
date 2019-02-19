(function () {
    'use strict';

    angular
        .module('app')
        .controller('OperationsController', OperationsController);

    OperationsController.$inject = ['SharesService', 'OperationsService'];
    function OperationsController(SharesService, OperationsService) {

        const vm = this;
        
        /***********************************************************************
         * Attributs du controller. */
       
        const DEFAULT_DEPOSIT = {rib: null, amount: null, label: null};
        const DEFAULT_TRANSFERT_DEBIT = {ribFrom: null, ribTo: null, 
            amount: null, label: null};
        const DEFAULT_PURCHASE_SALE = {rib: null, share: null, amount: null, 
            label: null};
        
        vm.shares = new Array();
        
        vm.deposit = angular.copy(DEFAULT_DEPOSIT);
        vm.transfertDebit = angular.copy(DEFAULT_TRANSFERT_DEBIT);
        vm.purchaseSale = angular.copy(DEFAULT_PURCHASE_SALE);
       
        let transfertOrDebit = null;
        let purchaseOrSale = null;
       
        /***********************************************************************
         * Constructeur du controller. */
        
        /* Initialise le controller. */
        (function() {
            getShares();
        })();

        /***********************************************************************
         * Methodes privees du controller. */
              
        function getShares() {
            SharesService.getShares().then(
                function(response) {
                    vm.shares = response;
                }
            );            
        }
        
        function clearDeposit() {
            vm.deposit = angular.copy(DEFAULT_DEPOSIT);
        }
        
        function clearTransfertDebit() {
            vm.transfertDebit = angular.copy(DEFAULT_TRANSFERT_DEBIT);
            transfertOrDebit = null;
        }
        
        function clearPurchaseSale() {
            vm.purchaseSale = angular.copy(DEFAULT_PURCHASE_SALE);
            purchaseOrSale = null;
        }
        
        /***********************************************************************
         * Methodes publiques du controller accesible a la vue. */
         
        vm.makeDeposit = function() {
            OperationsService.deposit(vm.deposit.rib, vm.deposit.amount, 
                vm.deposit.label).then(clearDeposit);
        };
        
        vm.chooseTransfert = function() {
            transfertOrDebit = 'TRANSFERT';
        };
        
        vm.chooseDebit = function() {
            transfertOrDebit = 'DEBIT';
        };
        
        vm.makeTransfertDebit = function() {            
            if (transfertOrDebit === 'TRANSFERT')
                OperationsService.transfert(
                    vm.transfertDebit.ribFrom, vm.transfertDebit.ribTo,
                    vm.transfertDebit.amount, vm.transfertDebit.label)
                        .then(clearTransfertDebit);
            else if (transfertOrDebit === 'DEBIT')
                OperationsService.debit(
                    vm.transfertDebit.ribFrom, vm.transfertDebit.ribTo,
                    vm.transfertDebit.amount, vm.transfertDebit.label)
                        .then(clearTransfertDebit);
        };
        
        vm.choosePurchase = function() {
            purchaseOrSale = 'PURCHASE';
        };
        
        vm.chooseSale = function() {
            purchaseOrSale = 'SALE';
        };
        
        vm.makePurchaseSale = function() {
            if (purchaseOrSale === 'PURCHASE')
                OperationsService.purchase(
                    vm.purchaseSale.rib, vm.purchaseSale.share,
                    vm.purchaseSale.amount, vm.purchaseSale.label)
                        .then(clearPurchaseSale);
            else if (purchaseOrSale === 'SALE')
                OperationsService.sale(
                    vm.purchaseSale.rib, vm.purchaseSale.share,
                    vm.purchaseSale.amount, vm.purchaseSale.label)
                        .then(clearPurchaseSale);
        };
         
    }

})();
