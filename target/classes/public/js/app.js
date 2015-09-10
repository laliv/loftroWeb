/**
 * Created by shekhargulati on 10/06/14.
 */
 
var app = angular.module('blog', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute'
]);
 
app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/list.html',
        controller: 'ListCtrl'
    }).when('/create', {
        templateUrl: 'views/create.html',
        controller: 'CreateCtrl'
    }).otherwise({
        redirectTo: '/'
    })
});
 
app.controller('ListCtrl', function ($scope, $http) {
    $http.get('/api/v1/blog').success(function (data) {
        $scope.todos = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })
 
    $scope.todoStatusChanged = function (blog) {
        console.log(blog);
        $http.put('/api/v1/blog/' + blog.id, todo).success(function (data) {
            console.log('status changed');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});
 
app.controller('CreateCtrl', function ($scope, $http, $location) {
    $scope.blog = {
        done: false
    };
 
    $scope.createTodo = function () {
        console.log($scope.blog);
        $http.post('/api/v1/blog', $scope.blog).success(function (data) {
            $location.path('/');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});