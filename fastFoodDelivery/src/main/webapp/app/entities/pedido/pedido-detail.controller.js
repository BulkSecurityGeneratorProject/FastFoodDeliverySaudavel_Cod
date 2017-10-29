(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('PedidoDetailController', PedidoDetailController);

    PedidoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pedido', 'Bebida', 'FormaDeEntrega', 'Alimento'];

    function PedidoDetailController($scope, $rootScope, $stateParams, previousState, entity, Pedido, Bebida, FormaDeEntrega, Alimento) {
        var vm = this;

        vm.pedido = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:pedidoUpdate', function(event, result) {
            vm.pedido = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
