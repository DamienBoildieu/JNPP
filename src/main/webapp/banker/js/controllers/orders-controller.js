(function () {
    'use strict';

    angular
        .module('app')
        .controller('OrdersController', OrdersController);

    OrdersController.$inject = ['OrdersService'];
    function OrdersController(OrdersService) {

        const vm = this;
        
        /***********************************************************************
         * Attributs du controller. */
        
        vm.orders = new Array();

        /***********************************************************************
         * Constructeur du controller. */
        
        /* Initialise le controller. */
        (function() {
            getOrders();
        })();

        /***********************************************************************
         * Methodes privees du controller. */
        
        /* Retourne toutes les commandes. */
        function getOrders() {
            OrdersService.getOrders().then(
                function(response) {
                    vm.orders = response;
                });
        }

        /***********************************************************************
         * Methodes publiques du controller accesible a la vue. */

        /* Fait avancer une commande et modifie la liste des commandes. */
        vm.upgradeOrder = function(index) {
            OrdersService.upgradeOrder(vm.orders[index].id).then(
                function(response) {
                    vm.orders[index] = response;
                });
        };
        
        /* Test si une commande peut etre avancee. */
        vm.canUpgrade = function(order) {
            return order.status === 'ORDERED' || order.status === 'ARRIVED';
        };

    }

})();

