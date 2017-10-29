(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('FormaDeEntregaDialogController', FormaDeEntregaDialogController);

    FormaDeEntregaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FormaDeEntrega'];

    function FormaDeEntregaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FormaDeEntrega) {
        var vm = this;

        vm.formaDeEntrega = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.formaDeEntrega.id !== null) {
                FormaDeEntrega.update(vm.formaDeEntrega, onSaveSuccess, onSaveError);
            } else {
                FormaDeEntrega.save(vm.formaDeEntrega, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fastFoodDeliveryApp:formaDeEntregaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
