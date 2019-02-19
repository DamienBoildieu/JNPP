(function () {
    'use strict';

    angular
        .module('app')
        .factory('FlashService', FlashService);

    FlashService.$inject = ['$rootScope', '$timeout'];
    function FlashService($rootScope, $timeout) {
        let service = {};

        service.Success = Success;
        service.Error = Error;
        service.ClearMessage = ClearMessage;
        
        initService();

        return service;

        function clearFlashMessage() {
            let flash = $rootScope.flash;
            if (flash) {
                if (!flash.keepAfterLocationChange) {
                    delete $rootScope.flash;
                } else {
                    // only keep for a single location change
                    flash.keepAfterLocationChange = false;
                }
            }
        }
        
        function initService() {
            $rootScope.$on('$locationChangeStart', function () {
                clearFlashMessage();
            });
        }

        function ClearMessage()
        {
            $rootScope.flash.message=' ';
        }
        
        function Success(message, keepAfterLocationChange) {
            $rootScope.flash = {
                message: message,
                type: 'success', 
                keepAfterLocationChange: keepAfterLocationChange
            };
            $timeout(function(){
                clearFlashMessage();
            }, 5000);
        }

        function Error(message, keepAfterLocationChange) {
            $rootScope.flash = {
                message: message,
                type: 'error',
                keepAfterLocationChange: keepAfterLocationChange
            };
            $timeout(function(){
                clearFlashMessage();
            }, 5000);
        }
    }

})();