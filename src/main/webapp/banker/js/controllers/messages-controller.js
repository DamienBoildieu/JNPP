(function () {
    'use strict';

    angular.module('app').controller('MessagesController', MessagesController);

    MessagesController.$inject = ['$location', '$timeout', 'LoginsService', 
        'MessagesService', 'DateService'];
    function MessagesController($location, $timeout, LoginsService,
            MessagesService, DateService) {

        const vm = this;
        
        /***********************************************************************
         * Attributs du controller. */
        
        /* Valeurs par defaut du formulaire rempli par l'utilisateur. */
        const DEFAULT_MESSAGE = {content: null};

        /* Liste des clients. */
        vm.clients = new Array();
        /* Arguments pour la recherche de clients. */
        vm.arguments = null;

        /* Discusion courrante. */
        vm.discusion = {
            client: null, 
            advisor: null, 
            messages: new Array()};
        /* Donnees du formulaire. */
        vm.message = angular.copy(DEFAULT_MESSAGE);

        /* Login du client de la discution courante.
         * Cette attribut n'est pas lie a vm car elle n'est pas necessaire a la 
         * vue. */
        let current = $location.search().login;
        /* Timestamp du dernier get des messages. */
        let timestamp = null;

        /***********************************************************************
         * Constructeur du controller. */
        
        /* Initialise le controller. */
        (function() {
            
            /* L'URL est nettoyee si elle contient un login en argument. */
            if (current) $location.search({});
            
            getClients();
            getDiscusion();
        })();

        /***********************************************************************
         * Methodes privees du controller. */
        
        /* Recupere la liste des clients. */
        function getClients() {
            LoginsService.getAll().then(
                    function(response) {
                        vm.clients = response;
                    });
        }
        
        /* Recupere la discution actuelle et affecte le timestamp a maintenant
         * et relance le timer si le get a ete effectue.
         * Cette fonction est pilotee par current et timestamp. 
         * Si le login associe a la discution courante est null, cette fonction 
         * ne fait rien.
         * Si le timestamp vaut null, toute la discussion est pull. Sinon seul 
         * la discution depuis le timestamp est pull. */
        function getDiscusion() {
            
            /* Pas de discution courante, la fonction de fait rien. */
            if (!current) return;
            
            if (!timestamp) {
                /* Le timestamp vaut null, toute la dicussion est pull. */
                MessagesService.getDiscusion(current).then(
                        function (response) {
                            vm.discusion.client = response.client;
                            vm.discusion.advisor = response.advisor;
                            vm.discusion.messages = response.messages;
                            timestamp = DateService.now();
                        },
                        clearDiscusion);
            } else {
                /* Ne devrait jamais arrive. */
                throw new Error("getDiscusion() while timestamp != null");
            }
        }

        /* Recuperes les messages de discution courrante et affecte le 
         * timestamp a maintenant.
         * Cette fonction est pilotee par current et timestamp.
         * Si timestamp vaut null, tous les messages sont pull, sinon seul les 
         * messages depuis le dernier timestamp sont pull. */
        function getMessages() {
            
            /* Pas de discution courante, la fonction de fait rien. */
            if (!current) return;
            
            if (!timestamp) {
                /* Le timestamp vaut null, tous les messages sont pull. */
                MessagesService.getMessages(current).then(
                        function (response) {
                            vm.discusion.messages = response;
                            timestamp = DateService.now();
                        });
            } else {
                /* Le timestamp n'est pas null, seul les messages depuis le 
                 * timestamp sont pull. */
                MessagesService.getMessagesFrom(current, timestamp).then(
                    function(response) {
                        vm.discusion.messages.push
                                .apply(vm.discusion.messages, response);
                        timestamp = DateService.now();
                    });
            }
        }

        /* Vide la discution courante. */
        function clearDiscusion() {
            vm.discusion.client = null;
            vm.discusion.advisor = null;
            vm.discusion.messages = new Array();
        }

        /* Vide le formulaire d'envoi. */
        function clearMessage() {
            vm.message = angular.copy(DEFAULT_MESSAGE);
        }
        
        /* Ajoute les messages a la discution courante et envoit le message. */
        function addMessagesAndSend(messages) {
            vm.discusion.messages.push.apply(vm.discusion.messages, messages);
            timestamp = DateService.now();
            MessagesService.sendMessage(current, vm.message.content).then(
                function(response) {
                    vm.discusion.messages.push(response);
                    vm.message = angular.copy(DEFAULT_MESSAGE);
                 });
        }

        /***********************************************************************
         * Methodes publiques du controller accesible a la vue. */

        /* Test si le login correspond au login de la dicution courante. */
        vm.isCurrent = function(login) {
            return login === current;
        };

        /* Affecte la dicusion courante. 
         * Si la discution courante change, le timestamp est mis a null forcant
         * le pull de l'entiere discusion lors du prochain pull, le formulaire 
         * d'envoie est nettoye et la nouvelle duscussion courante est pull. */
        vm.setCurrent = function(login) {
            if (current === login) return;
            current = login;
            timestamp = null;
            clearMessage();
            getDiscusion();
        };
        
        /* Test si le client match les arguments de la rercherche.
         * Si la recherche est, le client match. */
        vm.matchArguments = function(client) {
            return !vm.arguments || client.name.includes(vm.arguments) 
                    || client.email.includes(vm.arguments);
        };
        
        /* Envoit le message et recupere la discution.
         * Si le timestamp vaut null, toute la discution est recuperee. Sinon 
         * seul la discution depuis le dernier timestamp est recuperee.
         * Si current vaut null, rien n'est envoye. */
        vm.sendMessage = function() {
            
            /* Pas de discution courrante. */
            if (!current) return;
            
            if (!timestamp)
                /* Le timestamp vaut null, tous les messages sont pull. */
                MessagesService.getMessages(current).then(
                    function(response) {
                        addMessagesAndSend(response);
                    });
            else
                /* Le timestamp n'est pas null, seul les messages depuis le 
                 * dernier timestamp sont pull. */
                MessagesService.getMessagesSince(current, timestamp).then(
                    function(response) {
                        addMessagesAndSend(response);
                    });
        };

        /* Test si la discution courante est active. */
        vm.isDicusionActive = function() {
            return current;
        };
        
        /* Vide la recherche. */
        vm.clearArguments = function() {
            this.arguments = null;
        };

        /* Formate une date machine. */
        vm.formatDate = function(time) {
            return DateService.format(time);
        }

    }

})();
