(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AccountController', AccountController);
 
    AccountController.$inject = ['$scope', '$location', '$routeParams','AuthentificationService',
        'AccountService', 'PaymentMeanService', 'TranslatorService', 'FlashService'];
    
    function AccountController($scope, $location, $routeParams, AuthentificationService,
        AccountService, PaymentMeanService, TranslatorService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        
        $scope.movementsUrl = 'client/html/accounts/accountmovements.html';
        $scope.templateUrl = '';
        
        vm.closeAccount = closeAccount;
        vm.commandCard = commandCard;
        vm.commandCheckBook = commandCheckBook;
        
        init();
        
        function init() {
            AccountService.getAccount($routeParams.accountRib).then(
                function(response) {
                    $scope.account = response.account;
                    if ($scope.account.type==='CURRENT') {
                        $scope.templateUrl = 'client/html/accounts/currentaccount.html';
                        $scope.account.currency = TranslatorService.translateCurrency($scope.account.currency);
                    } else if ($scope.account.type==='JOINT') {
                        $scope.templateUrl = 'client/html/accounts/jointaccount.html';
                        $scope.account.currency = TranslatorService.translateCurrency($scope.account.currency);
                    } else if ($scope.account.type==='SAVING') {
                        $scope.templateUrl = 'client/html/accounts/savingaccount.html';
                        $scope.account.currency = TranslatorService.translateCurrency($scope.account.currency);
                    } else if ($scope.account.type==='SHARE') {
                        $scope.templateUrl = 'client/html/accounts/shareaccount.html';
                    } else {
                        FlashService.Error('Une erreurest présente dans le compte que vous souhaitez voir');
                        $location.path('/resume');
                    }
                    $scope.account.type = TranslatorService.translateAccount($scope.account.type);
                    $scope.movements = response.movements;
                    TranslatorService.transformMovements($scope.movements);
                },
                function(response) {
                    FlashService.Error(response);
                }
            );
            PaymentMeanService.getCardsByRib($routeParams.accountRib).then(
                function (response) {
                    $scope.cards = response;
                    for (let card of $scope.cards) {
                        card.status = TranslatorService.translatePaymentMeanStatus(card.status);
                    }
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
            PaymentMeanService.getCheckBooksByRib($routeParams.accountRib).then(
                function (response) {
                    $scope.checkBooks = response;
                    for (let checkBook of $scope.checkBooks) {
                        checkBook.status = TranslatorService.translatePaymentMeanStatus(checkBook.status);
                    }
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function closeAccount() {
            AccountService.closeAccount({rib: $scope.account.rib}).then(
                function () {
                    if (TranslatorService.untranslateAccount($scope.account.type)!==
                        'JOINT')
                        FlashService.Success('Votre compte a bien été supprimé', true);
                    else
                        FlashService.Success('Votre demande de suppression a bien été prise ' + 
                            'en compte, si vous êtes le dernier à avoir fait la demande, ' +
                            'le compte a été supprimé', true);
                    $location.path('/resume');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
            
        }
        
        function commandCard() {
            PaymentMeanService.commandCard({rib: $scope.account.rib}).then(
                function (card) {
                    card.status = TranslatorService.translatePaymentMeanStatus(card.status);
                    $scope.cards.push(card);                    ;
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function commandCheckBook() {
            PaymentMeanService.commandCheckBook({rib: $scope.account.rib}).then(
                function (checkBook) {
                    checkBook.status = TranslatorService.translatePaymentMeanStatus(checkBook.status);
                    $scope.checkBooks.push(checkBook);                    ;
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
    }
})();