(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('ValorRefeicaoDeleteController',ValorRefeicaoDeleteController);

    ValorRefeicaoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ValorRefeicao'];

    function ValorRefeicaoDeleteController($uibModalInstance, entity, ValorRefeicao) {
        var vm = this;

        vm.valorRefeicao = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ValorRefeicao.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
