(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('OpenAccountController', OpenAccountController);
 
    OpenAccountController.$inject = ['$rootScope', '$scope', 'AuthentificationService',
        'AccountService', 'UserService', 'TranslatorService', 'FlashService'];
    
    function OpenAccountController($rootScope, $scope, AuthentificationService,
        AccountService, UserService, TranslatorService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        
        $scope.nbClients = 1;
        $scope.genders = [];
        init();
        
        function init() {
            UserService.getGenders().then(
                function(genders) {
                    $scope.genders = TranslatorService.translateGenders(genders);
                    $(document).ready(function(){
                        $('select').formSelect();
                    });
                },
                function(errMsg) {
                    FlashService.Error(errMsg);
                }
            );
            if ($rootScope.globals.currentUser.type==='PRIVATE') {
                AccountService.getSavingBooks().then(
                    function (books) {
                        $scope.books = books;
                        $(document).ready(function(){
                            $('select').formSelect();
                        });
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
    }
})();