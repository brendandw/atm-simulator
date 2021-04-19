describe('atmControllerTest', function() {                
                var scope, controller;
                var atmService = {};
                var atmModel = {};
                var propertyExample = {};
                
                beforeEach(module('app.atm'));

                beforeEach(inject(function ($controller,$rootScope) {
                    scope = $rootScope.$new();
                    controller=$controller('atmController', {
                        '$scope': scope
                      })
                }));



                it('should not be null', function() {                   
                    expect(controller).not.toBe(null);
                });
                
                it('isNullOrUndefined should be true for null input', function() {                   
                    expect(scope.isNullOrUndefined(null)).toBe(true);
                });
                
                it('isNullOrUndefined should be true for undefined input', function() {                   
                    expect(scope.isNullOrUndefined(propertyExample.undefinedField)).toBe(true);
                });
                
                it('isNullOrUndefined should be false for valid input', function() {                   
                    expect(scope.isNullOrUndefined(propertyExample)).toBe(false);
                });
            });