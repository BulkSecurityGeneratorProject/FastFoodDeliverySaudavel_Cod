(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('TipoAlimentoDialogController', TipoAlimentoDialogController);

    TipoAlimentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoAlimento'];

    function TipoAlimentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoAlimento) {
        var vm = this;

        vm.tipoAlimento = entity;
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
            if (vm.tipoAlimento.id !== null) {
                TipoAlimento.update(vm.tipoAlimento, onSaveSuccess, onSaveError);
            } else {
                TipoAlimento.save(vm.tipoAlimento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fastFoodDeliveryApp:tipoAlimentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
