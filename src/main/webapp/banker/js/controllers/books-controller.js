(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('BooksController', BooksController);
 
    BooksController.$inject = ['$scope', 'BooksService'];
    function BooksController($scope, BooksService) {
        
        var vm = this;
        var defaultForm = {name: null, moneyRate: null, timeRate: null};
        
        $scope.books = new Array();
        $scope.data = angular.copy(defaultForm);
        
        vm.addBooks = addBooks;
        
        getBooks();
        
        function getBooks() {
            BooksService.getAll().then(
                function(books) {
                    $scope.books = books;
                }
            );            
        }
        
        function addBooks() {         
            BooksService.add($scope.data).then(
                function(book) {
                    $scope.books.push(book);
                    $scope.data = angular.copy(defaultForm);
                    $scope.form.$setPristine();
                    $scope.form.$setUntouched();
                }
            );
        }
    
    }
 
})();
