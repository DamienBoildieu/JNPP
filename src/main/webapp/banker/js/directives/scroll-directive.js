(function () {
    'use strict';

    /* Ajout d'une directive pour que la scrollbar soit a la fin de la 
     * discusion. */
    angular
        .module('app')        
        .directive('scroll', function($timeout) {
            return {
                restrict: 'A',
                link: function(scope, element, attr) {
                    scope.$watchCollection(attr.scroll, function() {
                        $timeout(function() {
                            element[0].scrollTop = element[0].scrollHeight;
                        });
                    });
                }
            };
        });

})();
