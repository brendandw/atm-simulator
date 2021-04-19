(function () {
    'use strict';
    angular.module('app.atm')
        .config(['$stateProvider','$urlRouterProvider',
            function($stateProvider, $urlRouterProvider)
            {
                $urlRouterProvider
                    .otherwise("/");
                $stateProvider
                    .state("root", {
                        url: "/",
                        templateUrl: "views/atm.html",
                        controller:"atmController as vm"
                    });
                    //.state("idGenerator", {
                    //    url: "/id-generator",
                    //    templateUrl: "views/idGenerator.html",
                    //    controller:"atmController as vm"
                    //});

            }])
        .run(['$state', function ($state)
        { }]);
})();
