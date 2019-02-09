(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('BannerController', BannerController);
    
    BannerController.$inject = ['$rootScope'];
    function BannerController($rootScope) {
        let loggedIn = $rootScope.globals.userName;
        if (loggedIn)
        	this.templateUrl = "html/connectedbanner.html";
        else
        	this.templateUrl = "html/unconnectedbanner.html";
    }
 
})();