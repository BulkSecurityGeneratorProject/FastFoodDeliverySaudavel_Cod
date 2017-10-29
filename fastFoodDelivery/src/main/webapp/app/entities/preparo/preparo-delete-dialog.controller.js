(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('PreparoDeleteController',PreparoDeleteController);

    PreparoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Preparo'];

    function PreparoDeleteController($uibModalInstance, entity, Preparo) {
        var vm = this;

        vm.preparo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Preparo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
