(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ManageUserController', ManageUserController);
 
    ManageUserController.$inject = ['$rootScope', '$scope', '$location', 'AuthentificationService',
        'UserService', 'TranslatorService', 'FlashService', 'NotifyService'];
    
    function ManageUserController($rootScope, $scope, $location, AuthentificationService, UserService,
        TranslatorService, FlashService, NotifyService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        
        $scope.updatePsswdData = {};
        $scope.closeData = {};
        vm.editInfo = editInfo;
        vm.updatePassword = updatePassword;
        vm.close = close;
        
        init();
        
        function init() {
            let type = $rootScope.globals.currentUser.type;
            $scope.info = $rootScope.globals.currentUser;
            switch (type) {
                case 'PRIVATE':
                    $scope.templateUrl = 'client/html/user/privateinfo.html';
                    if(!TranslatorService.isTranslatedGender($scope.info.identity.gender))
                        $scope.info.identity.gender = TranslatorService.translateGender($scope.info.identity.gender);
                    break;
                case 'PROFESIONAL':
                    $scope.templateUrl = 'client/prototypehtml/user/professionalinfo.html';
                    if(!TranslatorService.isTranslatedGender($scope.info.owner.gender))
                        $scope.info.owner.gender = TranslatorService.translateGender($scope.info.owner.gender);
                    break;
                default:
                    break;
            }
        }
        
        function editInfo() {
            switch ($scope.info.type) {
                 case 'PRIVATE':
                    $scope.info.identity.gender = TranslatorService.untranslateGender($scope.info.identity.gender);
                    break;
                case 'PROFESIONAL':
                    $scope.info.owner.gender = TranslatorService.untranslateGender($scope.info.owner.gender);
                    break;
                default:
                    break;
            }
            UserService.updateUserInfo($scope.info).then(
                function (response) {
                    FlashService.Success("Vos informations personnelles ont été mises à jour");
                    $scope.info = response;
                     switch ($scope.info.type) {
                        case 'PRIVATE':
                            $scope.info.identity.gender = TranslatorService.translateGender($scope.info.identity.gender);
                            break;
                        case 'PROFESIONAL':
                            $scope.info.owner.gender = TranslatorService.translateGender($scope.info.owner.gender);
                            break;
                        default:
                            break;
                    }
                    $rootScope.globals = {
                        currentUser : response
                    };
                },
                function (response) {
                    switch ($scope.info.type) {
                        case 'PRIVATE':
                            $scope.info.identity.gender = TranslatorService.translateGender($scope.info.identity.gender);
                            break;
                        case 'PROFESIONAL':
                            $scope.info.owner.gender = TranslatorService.translateGender($scope.info.owner.gender);
                            break;
                        default:
                            break;
                    }
                    FlashService.Error(response);
                }
            );
        }
        
        function updatePassword() {
            if ($scope.updatePsswdData.new===$scope.updatePsswdData.confirm) {
                UserService.updatePassword($scope.updatePsswdData.old, $scope.updatePsswdData.new).then(
                    function () {
                        FlashService.Success("Votre mot de passe a été mis à jour");
                    },
                    function (response) {
                        FlashService.Error(response);
                    }
                );
            } else {
                FlashService.Error('Les deux mots de passe entrés ne correspondent pas');
            }
        }
        
        function close() {
            if ($scope.closeData.psswd===$scope.closeData.confirm) {
                UserService.close($scope.closeData.psswd).then(
                    function () {
                        AuthentificationService.logout();
                        NotifyService.notify('logInOutEvent');
                        FlashService.Success('Votre compte a bien été supprimé', true);
                        $location.path('/welcome');
                    },
                    function (response) {
                        FlashService.Error(response);
                    }
                );                
            } else {
                FlashService.Error('Les deux mots de passe entrés ne correspondent pas');
            }

        }
    }
})();