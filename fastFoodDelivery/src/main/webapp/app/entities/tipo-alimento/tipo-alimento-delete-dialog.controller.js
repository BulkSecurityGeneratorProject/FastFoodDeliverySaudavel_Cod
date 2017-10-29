(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('TipoAlimentoDeleteController',TipoAlimentoDeleteController);

    TipoAlimentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoAlimento'];

    function TipoAlimentoDeleteController($uibModalInstance, entity, TipoAlimento) {
        var vm = this;

        vm.tipoAlimento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoAlimento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
