'use strict';

describe('Controller Tests', function() {

    describe('Refeicao Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRefeicao, MockValorRefeicao, MockTipoAlimento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRefeicao = jasmine.createSpy('MockRefeicao');
            MockValorRefeicao = jasmine.createSpy('MockValorRefeicao');
            MockTipoAlimento = jasmine.createSpy('MockTipoAlimento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Refeicao': MockRefeicao,
                'ValorRefeicao': MockValorRefeicao,
                'TipoAlimento': MockTipoAlimento
            };
            createController = function() {
                $injector.get('$controller')("RefeicaoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'fastFoodDeliveryApp:refeicaoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
