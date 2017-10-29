(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('AlimentoDeleteController',AlimentoDeleteController);

    AlimentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Alimento'];

    function AlimentoDeleteController($uibModalInstance, entity, Alimento) {
        var vm = this;

        vm.alimento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Alimento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
