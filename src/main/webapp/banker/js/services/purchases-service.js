(function () {
    'use strict';

    angular
        .module('app')
        .factory('PurchasesService', PurchasesService);

    PurchasesService.$inject = ['$http', '$q'];
    function PurchasesService($http, $q) {

        /***********************************************************************
         * Construction du service. */

        const service = {};

        service.getPurchases = getPurchases;
        service.upgradePurchase = upgradePurchase;
        
        return service;

        /***********************************************************************
         * Methodes du services. */
        
        /* Retourne la liste des commandes. */
        function getPurchases() {
            const url = 'http://localhost:8084/JNPP/banker/get-purchases.htm';
            const deferred = $q.defer();
            $http.get(url)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function () {
                    deferred.reject();
                }
            );
            return deferred.promise;
        }
        
        /* Fait avancer la commande identifiee par id. */
        function upgradePurchase(id) {
            const url = 'http://localhost:8084/JNPP/banker/upgrade-purchase.htm';
            const deferred = $q.defer();
            $http.post(url, {id: id}).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function () {
                    deferred.reject();
                }
            );
            return deferred.promise;
        }

    }

})();
