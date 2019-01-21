(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['$http', '$q'];
    
    function UserService($http, $q) {
        
        var service = {};
 
        service.GetAll = GetAll;
        service.GetAllFriends = GetAllFriends;
        service.GetByName = GetByName;
        
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;
        
        service.AddFriend = AddFriend;
        service.DeleteFriend = DeleteFriend;
        
        return service;
 
        function GetAll(username) {
            console.log('get all users');
            var deferred = $q.defer();
            $http({
            url : 'http://localhost:8080/UsersAngularJS/getAll.htm',
            method : "POST",
            data : {
                'login' : username,
            }
            }).then(
                        function (response) {
                            var users = response.data;
                            console.log('users:'+users);
                            deferred.resolve(users);
                        },
                        function (errResponse) {
                           console.error('Error while getting User : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
        }
 
        function GetAllFriends(username) {
            console.log('get all friends');
            var deferred = $q.defer();
            $http({
            url : 'http://localhost:8080/UsersAngularJS/getAllFriends.htm',
            method : "POST",
            data : {
                'login' : username,
            }
            }).then(
                        function (response) {
                            var users = response.data;
                            console.log('users friend:'+users);
                            deferred.resolve(users);
                        },
                        function (errResponse) {
                           console.error('Error while getting User : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
        }
        
        function GetByName(username) {
            console.log('get User');
            var deferred = $q.defer();
           /* $http({
            url : 'http://localhost:8080/UsersAngularJS/getUser.htm',
            method : "POST",
            data : {
                'login' : username,
            }
            }).then(
                        function (response) {
                            var user = response.data;
                            deferred.resolve(user);
                        },
                        function (errResponse) {
                            var user = null;
                           deferred.resolve(user);
                        }
                    );*/
            if (username==="damien") {
            	deferred.resolve("damien");
            } else {
            	deferred.reject("Tu n'es pas Damien");
            } 
            return deferred.promise;
        }
 
        function Create(user) {
            console.log('Creating User');
            var deferred = $q.defer();
            $http({
            url : 'http://localhost:8080/UsersAngularJS/userCreate.htm',
            method : "POST",
            data : {
                'login' : user.username,
                'pass' : user.password,
                'firstName' : user.firstName,
                'lastName' : user.lastName
            }
            }).then(
                        function (response) {
                            
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                           console.error('Error while creating User : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
        }
 
        function Update(user) {
           console.log('Creating User');
            var deferred = $q.defer();
            $http({
            url : 'http://localhost:8080/UsersAngularJS/userUpdate.htm',
            method : "PUT",
            data : {
                'login' : user.username,
                'pass' : user.password,
                'firstName' : user.firstName,
                'lastName' : user.lastName
            }
            }).then(
                        function (response) {
                            var user = response.data;
                            deferred.resolve(user);
                        },
                        function (errResponse) {
                           console.error('Error while creating User : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
        }
 
        function Delete(id)
        {
           console.log('Delete User');
            var deferred = $q.defer();
            $http({
            url : 'http://localhost:8080/UsersAngularJS/userDelete.htm',
            method : "POST",
            data : {
                'login' : id
            }
            }).then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating User : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
        }
 
        function AddFriend(id1, id2)
        {
            console.log('Add Friend');
            var deferred = $q.defer();
            $http({
            url : 'http://localhost:8080/UsersAngularJS/addFriend.htm',
            method : "POST",
            data : {
                'login' : id1,
                'loginFriend' : id2
            }
            }).then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while add Friend : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
        }
     
        //********
        // Todo
        //********
        function DeleteFriend(id1, id2)
        {
        }
    }
 
})();
 