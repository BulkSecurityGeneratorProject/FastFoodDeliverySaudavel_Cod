(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('ValorNutricionalDialogController', ValorNutricionalDialogController);

    ValorNutricionalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ValorNutricional'];

    function ValorNutricionalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ValorNutricional) {
        var vm = this;

        vm.valorNutricional = entity;
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
            if (vm.valorNutricional.id !== null) {
                ValorNutricional.update(vm.valorNutricional, onSaveSuccess, onSaveError);
            } else {
                ValorNutricional.save(vm.valorNutricional, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fastFoodDeliveryApp:valorNutricionalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
