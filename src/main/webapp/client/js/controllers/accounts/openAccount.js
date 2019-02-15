(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('OpenAccountController', OpenAccountController);
 
    OpenAccountController.$inject = ['$rootScope', '$scope', '$location', 'AuthentificationService',
        'AccountService', 'UserService', 'TranslatorService', 'FlashService'];
    
    function OpenAccountController($rootScope, $scope, $location, AuthentificationService,
        AccountService, UserService, TranslatorService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        
        $scope.nbClients = 1;
        $scope.genders = [];
        
        vm.openCurrentAccount = openCurrentAccount;
        vm.openSavingBook = openSavingBook;
        vm.subNbClients = subNbClients;
        vm.addNbClients = addNbClients;     
        
        init();
        
        function init() {
            UserService.getGenders().then(
                function(genders) {
                    $scope.genders = TranslatorService.translateGenders(genders);                  
                        $('select').formSelect();
                },
                function(errMsg) {
                    FlashService.Error(errMsg);
                }
            );
            if ($rootScope.globals.currentUser.type==='PRIVATE') {
                AccountService.getSavingBooks().then(
                    function (books) {
                        $scope.books = books;
                    },
                    function (response) {
                        FlashService.Error(response);
                    }
                );
            }
            $(document).ready(function(){
                $('.collapsible').collapsible();
            });
        }
        
        function openCurrentAccount() {
            AccountService.openCurrentAccount().then(
                function() {
                    FlashService.Success('Votre compte courrant a bien été ouvert', true);
                    $location.path('/resume');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function openSavingBook(bookName) {
            AccountService.openSavingAccount({bookName : bookName}).then(
                function() {
                    FlashService.Success('Votre livret a bien été ouvert', true);
                    $location.path('/resume');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function subNbClients() {
            if ($scope.nbClients > 1)
                $scope.nbClients--;
        }
        
        function addNbClients() {
            $scope.nbClients++;
                $('select').formSelect();
        }
    }
})();