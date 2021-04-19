/**
 * Created by brendandw on 2016/09/28.
 */
(function () {
    'use strict';

    angular
        .module('app.atm')
        .controller('atmController', ['$scope','$location','atmService','atmModel', atmController]);

    function atmController($scope,$location,atmService,atmModel) {
        console.log()
        var vm = this;
        var baseLocation=$location.protocol() + "://" + $location.host() + ":" + $location.port();
        var sock = new SockJS(baseLocation + '/atmWebSocket');




        vm.amountRequested="";
        vm.atmModel = [];
        vm.cashToAddList = [];
        vm.busyPopulatingAtm = false;
        vm.populateAtmError = false;
        vm.populateAtmErrorReason = "";
        vm.busyWithdrawingCash = false;
        vm.withdrawCashSuccess = false;
        vm.withdrawCashError = false;
        vm.withdrawCashOtherAmountAvailable = false;
        vm.withdrawCashErrorReason = "";
        vm.amountWithdrawn=0;
        vm.combinationWithdrawn=[];

        vm.combinationToRestore = [];
        vm.busyAddingCash = false;
        vm.addCashSuccess=false;
        vm.addCashError=false;
        vm.addCashErrorReason="";



        populateCashInAtm();



        function populateCashInAtm() {
            console.log("populating cash in ATM")
            resetPopulateAtmStatusVariables()
            getCashInAtmServiceCall().then(function(data) {
                console.log("then method hit")
                atmModel.setCashList(data);
                vm.atmModel = atmModel.cashList;
                vm.busyPopulatingAtm = false;
                vm.populateAtmError = false;
                console.log("vm.atmModel below")
                console.log(vm.atmModel)
            },function(error) {
                vm.populateAtmError = false;
            });
        }

        function getCashInAtmServiceCall() {
            return atmService.getCashInAtm();
        }

        $scope.withdrawCash = function(amountRequested) {
            console.log("withdrawing cash: "+amountRequested);
            resetAllWithdrawStatusVariables();
            resetAddCashStatusVariables();
            vm.busyWithdrawingCash = true;
            withdrawCashServiceCall(amountRequested).then(function(data) {
                console.log("cash withdrawn")
                console.log(data)
                vm.busyWithdrawingCash = false;
                vm.amountWithdrawn=data.amount;
                vm.combinationWithdrawn=data.cashList;
                vm.withdrawCashError = false;
                vm.withdrawCashErrorReason = "";
                console.log("withdrawCashSuccessful = "+vm.withdrawCashSuccess);
                console.log("vm.withdrawCashSuccess==true?"+(vm.withdrawCashSuccess==true));
                if (vm.amountWithdrawn==amountRequested) {
                    vm.withdrawCashOtherAmountAvailable=false;
                    populateCashInAtm();
                    vm.amountRequested="";
                    vm.withdrawCashSuccess = true;
                } else {
                    vm.withdrawCashOtherAmountAvailable=true;
                    vm.withdrawCashSuccess = false;
                }
            },function(error) {
                console.log("service error");
                vm.busyWithdrawingCash = false;
                vm.withdrawCashSuccess=false;
               vm.withdrawCashError = true;
               vm.withdrawCashErrorReason = "A technical error occurred, please try again later.";
            })
        }

        function withdrawCashServiceCall(amount) {
            return atmService.getWithdrawCash(amount);
        }

        $scope.withdrawOtherAmount = function(answer) {
            if (answer) {
                atmService.withdrawCashCombination(vm.combinationWithdrawn).then(function(success) {
                    if (success) {
                        vm.withdrawCashSuccess=true;
                        vm.withdrawCashOtherAmountAvailable=false;
                        vm.withdrawCashError = false;
                        vm.amountRequested="";
                        populateCashInAtm();
                    }
                },function(error) {
                    var amountToReport = "$"+vm.amountRequested;
                    vm.amountRequested="";
                    vm.withdrawCashOtherAmountAvailable=false;
                    vm.withdrawCashError = true;
                    vm.withdrawCashErrorReason = amountToReport +" is no longer available. Please try a different amount" ;
                })

            } else {
                vm.amountRequested="";
                resetAllWithdrawStatusVariables();
            }
        }


        $scope.addCashToAtm = function(combination) {
            resetAllWithdrawStatusVariables();
            resetAddCashStatusVariables();
            addCashServiceCall(combination).then(function(data) {
                vm.addCashSuccess=true;
                vm.addCashError=false;
                atmModel.setCashList(data);
                vm.atmModel=data;
                vm.busyAddingCash=false;
                vm.cashToAddList=[];
            },function (error) {
                vm.addCashError=true;
                vm.addCashSuccess=false;
            })
        }

        function addCashServiceCall(combination) {
            return atmService.postCash(combination);
        }

        $scope.refreshCashInAtm = function() {
            populateCashInAtm();
        }


        $scope.removeCombinationFromListToAdd = function(combination) {
            var index=vm.cashToAddList.indexOf(combination);
            vm.cashToAddList.splice(index,1);
        }

        $scope.addCombinationToListToAdd = function() {
            vm.cashToAddList.push({"denomination":0,"amountOfNotes":0});
        }

         $scope.appendNumber = function(num)  {
            vm.amountRequested+=num;
        };

        $scope.backspace = function(num)  {
            vm.amountRequested=vm.amountRequested.slice(0,-1);
        };

        $scope.keypressed = function(event) {
            if (event.keyCode == 13) {
                console.log("enter pressed")
            }
        }

        $scope.isNullOrUndefined = function(input) {
            if (angular.isUndefined(input)||input==null) {
                return true;
            }
            return false;
        }

        function resetAllWithdrawStatusVariables() {
            vm.busyWithdrawingCash = false;
            vm.withdrawCashSuccess = false;
            vm.withdrawCashError = false;
            vm.withdrawCashOtherAmountAvailable = false;
            vm.withdrawCashErrorReason = "";
        }

        function resetAddCashStatusVariables() {
            vm.busyAddingCash = false;
            vm.addCashSuccess=false;
            vm.addCashError=false;
            vm.addCashErrorReason="";
        }

        function resetPopulateAtmStatusVariables() {
            vm.busyPopulatingAtm = false;
            vm.populateAtmError = false;
            vm.populateAtmErrorReason = "";
        }

        sock.onmessage = function(e) {
            console.log("message received from websocket");
            atmModel.setCashList(JSON.parse(e.data));
            vm.atmModel=atmModel.cashList;
            $scope.$apply();
        }

    }
})();
