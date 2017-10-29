(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('FormaDeEntregaDeleteController',FormaDeEntregaDeleteController);

    FormaDeEntregaDeleteController.$inject = ['$uibModalInstance', 'entity', 'FormaDeEntrega'];

    function FormaDeEntregaDeleteController($uibModalInstance, entity, FormaDeEntrega) {
        var vm = this;

        vm.formaDeEntrega = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FormaDeEntrega.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
