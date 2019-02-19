(function () {
    'use strict';

    angular
        .module('app')
        .factory('OrdersService', OrdersService);

    OrdersService.$inject = ['RequestsService'];
    function OrdersService(RequestsService) {

        /***********************************************************************
         * Construction du service. */

        const service = {};

        service.getOrders = getOrders;
        service.upgradeOrder = upgradeOrder;
        
        return service;

        /***********************************************************************
         * Methodes du services. */
        
        /* Retourne la liste des commandes. */
        function getOrders() {
            const url = RequestsService.url() + 'get-orders.htm';
            return RequestsService.get(url);
        }
        
        /* Fait avancer la commande identifiee par id. */
        function upgradeOrder(id) {
            const url = RequestsService.url() + 'upgrade-order.htm';
            return RequestsService.post(url, {id: id});
        }

    }

})();
