function FeedListCtrl($scope, $routeParams, feed, refreshFeed, trello, trelloMember) {
    var types = [
        {"name": "News", "selected": true},
        {"name": "Article", "selected": true},
        {"name": "Interview", "selected": true},
        {"name": "Presentation", "selected": true}
    ];
    $scope.types = types;

    var feedPage;
    if ($routeParams.page) {
        feedPage = $routeParams.page;
    } else {
        feedPage = 0;
    }
    $scope.feeds = feed.query({"page": feedPage}, function (f) {
        if (f.length === 0) {
            $scope.refreshFeed();
        } else {
            $scope.feeds = f;
        }
    });

    $scope.alerts = [];

    $scope.connected = false;

    $scope.userinfo = trelloMember.query(function () {
        $scope.connected = true;
    });

    $scope.formatDate = function (date) {
        return new Date(date).toLocaleDateString();
    };

    $scope.formatCategories = function (categories) {
        var s = "";
        var append = "";
        for (i = 0; i < categories.length; i++) {
            s += append;
            append = ", ";
            s += categories[i];
        }
        return s;
    };

    $scope.addToTrello = function (index) {
        trello.add($scope.feeds.content[index], function (result) {
            if (result.result != "") {
                window.location = window.location.pathname + result.result;
            } else {
                $scope.alerts.push({"title": "Carte créée dans Trello", "type": "success", "content": ""});
                $scope.feeds = feed.query({"page": feedPage}, function (f) {
                    $scope.feeds = f;
                });
            }
        }, function (error) {
            $scope.alerts.push({"title": "Erreur lors de la création de la carte dans Trello", "type": "error", "content": ""});
        });
    };

    $scope.filterCategory = function (entryToFilter) {
        if (entryToFilter.type) {
            for (var i = 0; i < types.length; i++) {
                if (types[i].selected === true && types[i].name === entryToFilter.type) {
                    return true;
                }
            }
        }
        return false;
    };

    $scope.refreshFeed = function () {
        refreshFeed.query(function (f) {
            $scope.feeds = f;
            $scope.alerts.push({"title": "Mise à jour terminé", "type": "success", "content": ""});
        });
    };
}

function StatsCtrl($scope, trelloList) {
    trelloList.query(function (l) {
        if (l.result != null && l.result != "") {
            window.location = window.location.pathname + l.result;
        } else {
            $scope.lists = l.body;
        }
    });
}

