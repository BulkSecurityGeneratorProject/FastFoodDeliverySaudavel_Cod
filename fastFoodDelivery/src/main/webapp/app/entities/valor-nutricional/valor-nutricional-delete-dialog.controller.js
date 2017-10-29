(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('ValorNutricionalDeleteController',ValorNutricionalDeleteController);

    ValorNutricionalDeleteController.$inject = ['$uibModalInstance', 'entity', 'ValorNutricional'];

    function ValorNutricionalDeleteController($uibModalInstance, entity, ValorNutricional) {
        var vm = this;

        vm.valorNutricional = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ValorNutricional.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
