(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('BebidaDeleteController',BebidaDeleteController);

    BebidaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Bebida'];

    function BebidaDeleteController($uibModalInstance, entity, Bebida) {
        var vm = this;

        vm.bebida = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Bebida.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
