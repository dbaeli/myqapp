function HeaderController($scope, $location, UserService) {
    $scope.isActive = function (route) {
        return $location.path().indexOf(route) === 0;
    };

    $scope.userinfo = UserService.query();

    $scope.$on('$routeChangeStart', function (next, current) {
        UserService.query(function (response) {
            $scope.userinfo = response;
        }, function (response) {
            if (response.status === 400 || response.status === 401) {
                window.location = window.location.pathname + "google-signin.html";
            } else if (response.status === 403) {
                window.location = window.location.pathname + "trello-token.html";
            }
        });
    });

}

