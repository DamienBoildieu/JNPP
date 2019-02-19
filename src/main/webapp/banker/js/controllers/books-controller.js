(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('BooksController', BooksController);
 
    BooksController.$inject = ['$scope', 'BooksService'];
    function BooksController($scope, BooksService) {
        
        var vm = this;
        var defaultForm = {name: null, moneyRate: null, timeRate: null};
        
        vm.books = new Array();
        vm.data = angular.copy(defaultForm);
        
        vm.addBooks = addBooks;
        
        getBooks();
        
        function getBooks() {
            BooksService.getAll().then(
                function(books) {
                    vm.books = books;
                }
            );            
        }
        
        function addBooks() {         
            BooksService.add(vm.data).then(
                function(book) {
                    vm.books.push(book);
                    vm.data = angular.copy(defaultForm);
                    $scope.form.$setPristine();
                    $scope.form.$setUntouched();
                }
            );
        }
    
    }
 
})();
