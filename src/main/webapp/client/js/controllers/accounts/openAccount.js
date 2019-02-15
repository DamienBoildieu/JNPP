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
        
        $scope.genders = [];
        $scope.jointAccountDatas = [{}];
        
        vm.openCurrentAccount = openCurrentAccount;
        vm.openSavingBook = openSavingBook;
        vm.subNbClients = subNbClients;
        vm.addNbClients = addNbClients;     
        vm.openJointAccount = openJointAccount;
        
        init();
        
        function init() {
            UserService.getGenders().then(
                function(genders) {
                    $scope.genders = TranslatorService.translateGenders(genders);                  
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
        
        function openJointAccount() {
            console.log($scope.jointAccountDatas);
            $scope.jointAccountDatas.push($rootScope.globals.currentUser.identity);
            console.log($scope.jointAccountDatas);
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
        
        function subNbClients() {
            if ($scope.jointAccountDatas.length>1)
                $scope.jointAccountDatas.pop();
        }
        
        function addNbClients() {
            $scope.jointAccountDatas.push({});
        }
    }
})();