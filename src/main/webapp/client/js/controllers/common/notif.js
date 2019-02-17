(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('NotifController', BannerController);
    
    NotifController.$inject = ['$scope', '$location', 
        'NotifyService', 'AuthentificationService', 'FlashService'];
    function NotifController($scope, $location, NotifyService,
        AuthentificationService, FlashService) {
        let vm = this;
        
        init();
                
        function init() {
            
        }
    }
 
})();