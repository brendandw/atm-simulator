(function () {
    'use strict';

    angular.module('app.atm')
        .directive('passFocusTo', function ($timeout) {
            return {
                link: function (scope, element, attrs) {
                    element.bind('click', function () {
                        $timeout(function () {
                            var elem = element.parent();
                            while(elem[0].id != attrs.focusParent) {
                                elem = elem.parent();
                            }
                            elem.find("#"+attrs.passFocusTo)[0].focus();
                        });
                    });
                }
            };
        });
})();