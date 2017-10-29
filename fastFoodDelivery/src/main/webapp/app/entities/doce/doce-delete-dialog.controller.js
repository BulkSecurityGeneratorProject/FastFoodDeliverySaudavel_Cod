(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('DoceDeleteController',DoceDeleteController);

    DoceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Doce'];

    function DoceDeleteController($uibModalInstance, entity, Doce) {
        var vm = this;

        vm.doce = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Doce.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
