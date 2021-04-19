(function () {
    'use strict';

    angular.module('app.atm')
    .directive('keypressEvents', function ($document) {
            return {
                restrict: 'A',
                link: function () {
                    $document.bind('keypress', function (e) {
                        alert(e.keyCode);
                    });
                }
            }
        }
    )
})();

