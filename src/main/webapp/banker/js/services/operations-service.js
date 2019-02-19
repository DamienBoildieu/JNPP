(function () {
    'use strict';

    angular
        .module('app')
        .factory('OperationsService', OperationsService);

    OperationsService.$inject = ['RequestsService'];
    function OperationsService(RequestsService) {

        /***********************************************************************
         * Construction du service. */

        const service = {};

        service.deposit = deposit;
        service.transfert = transfert;
        service.debit = debit;
        service.purchase = purchase;
        service.sale = sale;
        
        return service;

        /***********************************************************************
         * Methodes du services. */
        
        function deposit(rib, amount, label) {
            const url = RequestsService.url() + 'deposit.htm';
            const datas = {rib: rib, amount: amount, label: label};
            return RequestsService.post(url, {deposit: datas});
        }
            
        function transfert(ribFrom, ribTo, amount, label) {
            const url = RequestsService.url() + 'transfert.htm';
            const datas = {ribFrom: ribFrom, ribTo: ribTo, amount: amount, 
                label: label};
            return RequestsService.post(url, {transfert: datas});
        }
            
        function debit(ribFrom, ribTo, amount, label) {
            const url = RequestsService.url() + 'debit.htm';
            const datas = {ribFrom: ribFrom, ribTo: ribTo, amount: amount, 
                label: label};
            return RequestsService.post(url, {debit: datas});
        }
            
        function purchase(rib, share, amount, label) {
            const url = RequestsService.url() + 'purchase.htm';
            const datas = {rib: rib, share: share, amount: amount, 
                label: label};
            return RequestsService.post(url, {purchase: datas});
        }
            
        function sale(rib, share, amount, label) {
            const url = RequestsService.url() + 'sale.htm';
            const datas = {rib: rib, share: share, amount: amount, 
                label: label};
            return RequestsService.post(url, {sale: datas});
        }

    }

})();

