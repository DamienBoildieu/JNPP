(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('PaymentMeanService', PaymentMeanService);
 
    PaymentMeanService.$inject = ['$http', '$q', 'CommonService'];
    
    function PaymentMeanService($http, $q, CommonService) {
        
        let service = {};
        
        service.getClientCards = getClientCards;
        service.getCardsByRib = getCardsByRib;
        service.getClientCheckBooks = getClientCheckBooks;
        service.getCheckBooksByRib = getCheckBooksByRib;
        service.commandCard = commandCard;
        service.commandCheckBook = commandCheckBook;
        
        return service;
        
        function getClientCards() {
            return CommonService.basicGetRequest('clientCards.htm');
        }
        
        function getCardsByRib(rib) {
            let url = CommonService.basePath+'cards/'+rib+'.htm';
            let deferred = $q.defer();
            $http.get(url).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (response) {
                    deferred.reject(response.data);
                }
            );
            return deferred.promise;
        }
        
        function getClientCheckBooks() {
            return CommonService.basicGetRequest('clientCheckBooks.htm');
        }
        
        function getCheckBooksByRib(rib) {
            let url = CommonService.basePath+'checkBooks/'+rib+'.htm';
            let deferred = $q.defer();
            $http.get(url).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (response) {
                    deferred.reject(response.data);
                }
            );
            return deferred.promise;
        }
        
        function commandCard(data) {
            return CommonService.basicPostRequest('commandCard.htm', data);
        }
        
        function commandCheckBook(data) {
            return CommonService.basicPostRequest('commandCheckBook.htm', data);
        }
    }
})();