(function () {
    'use strict';

    angular.module('app').controller('PurchasesController', PurchasesController);

    PurchasesController.$inject = ['PurchasesService'];
    function PurchasesController(PurchasesService) {

        const vm = this;
        
        /***********************************************************************
         * Attributs du controller. */
        
        vm.purchases = new Array();

        /***********************************************************************
         * Constructeur du controller. */
        
        /* Initialise le controller. */
        (function() {
            getPurchases();
        })();

        /***********************************************************************
         * Methodes privees du controller. */
        
        /* Retourne toutes les commandes. */
        function getPurchases() {
            PurchasesService.getPurchases().then(
                function(response) {
                    vm.purchases = response;
                });
        }

        /***********************************************************************
         * Methodes publiques du controller accesible a la vue. */

        /* Fait avancer une commande et modifie la liste des commandes. */
        vm.upgradePurchase = function(index) {
            PurchasesService.upgradePurchase(vm.purchases[index].id).then(
                function(response) {
                    vm.purchases[index] = response;
                });
        }
        
        /* Test si une commande peut etre avancee. */
        vm.canUpgrade = function(purchase) {
            return purchase.status === 'ORDERED' 
                    || purchase.status === 'ARRIVED';
        }

    }

})();

