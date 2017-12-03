(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('AcompanharPedidoController', PedidoController);

    PedidoController.$inject = ['$state', 'Pedido'];

    function PedidoController($state, Pedido) {

        var vm = this;

        vm.pedidos = Pedido.query();

    }
})();
