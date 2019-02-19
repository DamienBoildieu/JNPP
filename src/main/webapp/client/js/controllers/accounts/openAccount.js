(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('OpenAccountController', OpenAccountController);
 
    /**
     * Controleur de la vue d'ouverture de compte
     */
    OpenAccountController.$inject = ['$rootScope', '$scope', '$location', 'AuthentificationService',
        'AccountService', 'UserService', 'TranslatorService', 'FlashService'];   
    function OpenAccountController($rootScope, $scope, $location, AuthentificationService,
        AccountService, UserService, TranslatorService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        
        $scope.genders = [];
        $scope.jointAccountDatas = [{}];
        
        vm.openCurrentAccount = openCurrentAccount;
        vm.openSavingBook = openSavingBook;
        vm.subNbClients = subNbClients;
        vm.addNbClients = addNbClients;     
        vm.openJointAccount = openJointAccount;
        vm.openShareAccount = openShareAccount;
        
        init();
        /**
         * Recupere les sexes pour l'ajout de personnes dans un compte joint
         * ainsi que les livrets
         */
        function init() {
            if ($rootScope.globals.currentUser.type==='PRIVATE') {
                UserService.getGenders().then(
                    function(genders) {
                        $scope.genders = TranslatorService.translateGenders(genders);
                        $scope.jointAccountDatas[0] = {gender: $scope.genders[0].key};
                        setTimeout(function () {
                                $('select').formSelect();
                        }, 200);
                    },
                    function(errMsg) {
                        FlashService.Error(errMsg);
                    }
                );
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
        
        function openJointAccount() {
            $scope.jointAccountDatas.push($rootScope.globals.currentUser.identity);
            AccountService.openJointAccount($scope.jointAccountDatas).then(
                function() {
                    FlashService.Success('Votre compte joint a bien été ouvert', true);
                    $scope.jointAccountDatas = [{}];
                    $location.path('/resume');
                },
                function (response) {
                    $scope.jointAccountDatas.pop();
                    FlashService.Error(response);
                }
            );
        }
        
        function openShareAccount() {
            AccountService.openShareAccount().then(
                function() {
                    FlashService.Success('Votre compte titres a bien été ouvert', true);
                    $location.path('/resume');
                },
                function (response) {
                    FlashService.Error(response);
                }
            );
        }
        
        function subNbClients() {
            if ($scope.jointAccountDatas.length>1)
                $scope.jointAccountDatas.pop();
        }
        
        function addNbClients() {
            if ($scope.genders.length>0) {
                $scope.jointAccountDatas.push({gender: $scope.genders[0].key});
                setTimeout(function () {
                    $('select').formSelect();
                }, 200);
            }
            else
                $scope.jointAccountDatas.push({});
        }
    }
})();