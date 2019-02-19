(function () {
    'use strict';

    angular
        .module('app')
        .factory('MessagesService', MessagesService);

    MessagesService.$inject = ['RequestsService'];
    function MessagesService(RequestsService) {

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
            const url = RequestsService.url() + 'get-discusion.htm';
            return RequestsService.get(url, {login: login});
        }
        
        /* Retourne les messages du client designe par le login. */
        function getMessages(login) {
            const url = RequestsService.url() + 'get-messages.htm';
            return RequestsService.get(url, {login: login});
        }

        /* Retourne les messages du client designe par le login depuis le 
         * timestamp. */
        function getMessagesSince(login, timestamp) {
            const url = RequestsService.url() + 'get-messages-since.htm';
            return RequestsService
                    .get(url, {login: login, timestamp: timestamp});
        }
        
        /* Envoie un message au client designe par login et retourne le message
         * envoye. */
        function sendMessage(login, content) {
            const url = RequestsService.url() + 'send-message.htm';
            return RequestsService.post(url, {login: login, content: content});
        }

    }

})();
