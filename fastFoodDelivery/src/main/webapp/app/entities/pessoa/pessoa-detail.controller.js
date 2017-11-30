(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('PessoaDetailController', PessoaDetailController);

    PessoaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pessoa', 'Endereco', 'Cartao', 'Pedido', 'Cliente'];

    function PessoaDetailController($scope, $rootScope, $stateParams, previousState, entity, Pessoa, Endereco, Cartao, Pedido, Cliente) {
        var vm = this;

        vm.pessoa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:pessoaUpdate', function(event, result) {
            vm.pessoa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
