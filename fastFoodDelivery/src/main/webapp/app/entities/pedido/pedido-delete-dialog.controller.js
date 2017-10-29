(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('PedidoDeleteController',PedidoDeleteController);

    PedidoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pedido'];

    function PedidoDeleteController($uibModalInstance, entity, Pedido) {
        var vm = this;

        vm.pedido = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pedido.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
