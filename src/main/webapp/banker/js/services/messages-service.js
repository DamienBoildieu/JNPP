(function () {
    'use strict';

    angular
        .module('app')
        .factory('MessagesService', MessagesService);

    MessagesService.$inject = ['$http', '$q'];
    function MessagesService($http, $q) {

        /***********************************************************************
         * Construction du service. */

        const service = {};

        service.getDiscusion = getDiscusion;
        service.getMessages = getMessages;
        service.getMessagesSince = getMessagesSince;
        service.sendMessage = sendMessage;
        
        return service;

        /***********************************************************************
         * Methodes du services. */
        
        /* Retourne la discusion du client designe par le login. 
         * Une discusion comprend, le client, le conseiller et les messages. */
        function getDiscusion(login) {
            const url = 'http://localhost:8084/JNPP/banker/get-discusion.htm';
            const deferred = $q.defer();
            $http.get(url, {params: {login: login}})
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
        
        /* Retourne les messages du client designe par le login. */
        function getMessages(login) {
            const url = 'http://localhost:8084/JNPP/banker/get-messages.htm';
            const deferred = $q.defer();
            $http.get(url, {params: {login: login}}).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function () {
                    deferred.reject();
                }
            );
            return deferred.promise;
        }

        /* Retourne les messages du client designe par le login depuis le 
         * timestamp. */
        function getMessagesSince(login, timestamp) {
            console.log('messages since ' + timestamp);
            const url = 'http://localhost:8084/JNPP/banker/get-messages-since.htm';
            const deferred = $q.defer();
            $http.get(url, {params: {login: login, timestamp: timestamp}}).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function () {
                    deferred.reject();
                }
            );
            return deferred.promise;
        }
        
        /* Envoie un message au client designe par login et retourne le message
         * envoye. */
        function sendMessage(login, content) {
            const url = 'http://localhost:8084/JNPP/banker/send-message.htm';
            const deferred = $q.defer();
            $http.post(url, {login: login, content: content}).then(
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
