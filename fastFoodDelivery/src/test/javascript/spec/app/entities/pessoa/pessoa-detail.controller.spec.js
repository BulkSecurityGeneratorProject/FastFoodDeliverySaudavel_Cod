'use strict';

describe('Controller Tests', function() {

    describe('Pessoa Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPessoa, MockEndereco, MockCartao, MockPedido, MockCliente;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPessoa = jasmine.createSpy('MockPessoa');
            MockEndereco = jasmine.createSpy('MockEndereco');
            MockCartao = jasmine.createSpy('MockCartao');
            MockPedido = jasmine.createSpy('MockPedido');
            MockCliente = jasmine.createSpy('MockCliente');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Pessoa': MockPessoa,
                'Endereco': MockEndereco,
                'Cartao': MockCartao,
                'Pedido': MockPedido,
                'Cliente': MockCliente
            };
            createController = function() {
                $injector.get('$controller')("PessoaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'fastFoodDeliveryApp:pessoaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
