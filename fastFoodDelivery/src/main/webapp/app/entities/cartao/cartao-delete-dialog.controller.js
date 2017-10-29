(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('CartaoDeleteController',CartaoDeleteController);

    CartaoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cartao'];

    function CartaoDeleteController($uibModalInstance, entity, Cartao) {
        var vm = this;

        vm.cartao = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cartao.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
