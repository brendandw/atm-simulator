(function () {
    'use strict';

    angular
        .module('app.atm')
        .factory('atmService', ['$http','$q',atmService]);

    function atmService($http,$q) {
        var urlBase = '/atm/';

        return {
            getWithdrawCash: getWithdrawCash,
            getCashInAtm: getCashInAtm,
            postCash: postCash,
            withdrawCashCombination: withdrawCashCombination
        };

        function getWithdrawCash(amount)
        {
            return $http.get(urlBase+"withdraw/"+amount).then(success,fail);
        };

        function getCashInAtm()
        {
            return $http.get(urlBase+"getCashInAtm").then(success,fail);
        };

        function withdrawCashCombination(cashList) {
            console.log("trying to withdraw combination");
            return  $http({
            method: 'Post',
                url: urlBase+"withdrawCashCombination/",
            data: cashList
            })
                .then(success)
                .catch(fail);
        };

        function postCash(cashList) {
            return  $http({
            method: 'Post',
                url: urlBase+"addCashToAtm/",
            data: cashList
            })
                .then(success)
                .catch(fail);
        };


        function success(response) {
            return response.data;
        }

        function fail(error) {
            return $q.reject(error);
        }

    }

})();
