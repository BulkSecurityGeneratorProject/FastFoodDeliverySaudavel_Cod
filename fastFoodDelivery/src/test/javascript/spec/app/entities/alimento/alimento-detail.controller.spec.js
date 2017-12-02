'use strict';

describe('Controller Tests', function() {

    describe('Alimento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAlimento, MockPreparo, MockTempero, MockValorNutricional, MockTipoAlimento, MockValorRefeicao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAlimento = jasmine.createSpy('MockAlimento');
            MockPreparo = jasmine.createSpy('MockPreparo');
            MockTempero = jasmine.createSpy('MockTempero');
            MockValorNutricional = jasmine.createSpy('MockValorNutricional');
            MockTipoAlimento = jasmine.createSpy('MockTipoAlimento');
            MockValorRefeicao = jasmine.createSpy('MockValorRefeicao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Alimento': MockAlimento,
                'Preparo': MockPreparo,
                'Tempero': MockTempero,
                'ValorNutricional': MockValorNutricional,
                'TipoAlimento': MockTipoAlimento,
                'ValorRefeicao': MockValorRefeicao
            };
            createController = function() {
                $injector.get('$controller')("AlimentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'fastFoodDeliveryApp:alimentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
