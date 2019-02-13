(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ManageUserController', ManageUserController);
 
    ManageUserController.$inject = ['$rootScope', '$location', 'AuthentificationService',
        'UserService', 'TranslatorService', 'FlashService'];
    
    function ManageUserController($rootScope, $location, AuthentificationService, UserService,
        TranslatorService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        
        vm.changePsswdData = {};
        vm.closeData = {};
        vm.editInfo = editInfo;
        vm.close = close;
        
        init();
        
        function init() {
            let type = $rootScope.globals.currentUser.type;
            vm.info = $rootScope.globals.currentUser;
            switch (type) {
                case 'PRIVATE':
                    vm.templateUrl = 'html/user/privateinfo.html';
                    if(!TranslatorService.isTranslatedGender(vm.info.identity.gender))
                        vm.info.identity.gender = TranslatorService.translateGender(vm.info.identity.gender);
                    break;
                case 'PROFESIONAL':
                    vm.templateUrl = 'html/user/professionalinfo.html';
                    if(!TranslatorService.isTranslatedGender(vm.info.owner.gender))
                        vm.info.owner.gender = TranslatorService.translateGender(vm.info.owner.gender);
                    break;
                default:
                    break;
            }
        }
        
        function editInfo() {
            switch (vm.info.type) {
                 case 'PRIVATE':
                    vm.info.identity.gender = TranslatorService.untranslateGender(vm.info.identity.gender);
                    break;
                case 'PROFESIONAL':
                    vm.info.owner.gender = TranslatorService.untranslateGender(vm.info.owner.gender);
                    break;
                default:
                    break;
            }
            UserService.updateUserInfo(vm.info).then(
                function (response) {
                    FlashService.Success("Vos infirmations personnelles ont été mises à jour");
                    vm.info = response;
                     switch (vm.info.type) {
                        case 'PRIVATE':
                            vm.info.identity.gender = TranslatorService.translateGender(vm.info.identity.gender);
                            break;
                        case 'PROFESIONAL':
                            vm.info.owner.gender = TranslatorService.translateGender(vm.info.owner.gender);
                            break;
                        default:
                            break;
                    }
                    AuthentificationService.setCredentials(response);
                },
                function (response) {
                    switch (vm.info.type) {
                        case 'PRIVATE':
                            vm.info.identity.gender = TranslatorService.translateGender(vm.info.identity.gender);
                            break;
                        case 'PROFESIONAL':
                            vm.info.owner.gender = TranslatorService.translateGender(vm.info.owner.gender);
                            break;
                        default:
                            break;
                    }
                    FlashService.Error(response);
                }
            );
        }
        
        function close() {
            console.log(vm.closeData);
            if (vm.closeData.psswd===vm.closeData.confirm) {
                UserService.close(vm.closeData.psswd).then(
                    function () {
                        AuthentificationService.logout();
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