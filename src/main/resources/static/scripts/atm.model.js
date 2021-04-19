(function () {
    'use strict';

    angular
        .module('app.atm')
        .factory('atmModel',atmModel);

    function atmModel() {
        var model = {};

        model.cashList=[];
        model.cashToAddList=[];

        model.setCashList=function(cashList){
            model.cashList = cashList;
            console.log("model.cashList below")
            console.log(model.cashList )
        };

        model.setCashToAddList=function(cashList){
            model.cashList = cashList;
        };


        return model;

}
})();
