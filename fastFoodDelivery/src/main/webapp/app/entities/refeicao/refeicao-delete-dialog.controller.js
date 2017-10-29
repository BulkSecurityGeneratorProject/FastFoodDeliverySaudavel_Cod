(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('RefeicaoDeleteController',RefeicaoDeleteController);

    RefeicaoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Refeicao'];

    function RefeicaoDeleteController($uibModalInstance, entity, Refeicao) {
        var vm = this;

        vm.refeicao = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Refeicao.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
