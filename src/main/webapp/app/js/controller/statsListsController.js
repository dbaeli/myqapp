function StatsListCtrl($scope, StatsLists) {
    $scope.lists = StatsLists.query();

    $scope.predicate = $scope.name;
    $scope.reverse = true;

    $scope.news = function(stats) {
        return stats.originalNews + stats.translatedNews;
    };

    $scope.articles = function(stats){
        return stats.originalArticles + stats.translatedArticles;
    };

    $scope.valids = function(stats){
        return stats.validatedArticles + stats.validatedNews;
    };

    $scope.mentors = function(stats){
        return stats.mentoredNews + stats.mentoredArticles;
    };

    $scope.name = function(stats){
        return stats.member.fullName;
    }

}