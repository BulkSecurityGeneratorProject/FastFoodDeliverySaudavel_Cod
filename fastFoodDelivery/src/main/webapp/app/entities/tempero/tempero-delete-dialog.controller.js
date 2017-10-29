(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('TemperoDeleteController',TemperoDeleteController);

    TemperoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tempero'];

    function TemperoDeleteController($uibModalInstance, entity, Tempero) {
        var vm = this;

        vm.tempero = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tempero.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
