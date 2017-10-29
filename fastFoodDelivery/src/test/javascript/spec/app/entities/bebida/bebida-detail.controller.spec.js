'use strict';

describe('Controller Tests', function() {

    describe('Bebida Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBebida, MockDoce, MockValorNutricional, MockValorRefeicao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBebida = jasmine.createSpy('MockBebida');
            MockDoce = jasmine.createSpy('MockDoce');
            MockValorNutricional = jasmine.createSpy('MockValorNutricional');
            MockValorRefeicao = jasmine.createSpy('MockValorRefeicao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Bebida': MockBebida,
                'Doce': MockDoce,
                'ValorNutricional': MockValorNutricional,
                'ValorRefeicao': MockValorRefeicao
            };
            createController = function() {
                $injector.get('$controller')("BebidaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'fastFoodDeliveryApp:bebidaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
