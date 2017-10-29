(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('TemperoDialogController', TemperoDialogController);

    TemperoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tempero'];

    function TemperoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tempero) {
        var vm = this;

        vm.tempero = entity;
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
            if (vm.tempero.id !== null) {
                Tempero.update(vm.tempero, onSaveSuccess, onSaveError);
            } else {
                Tempero.save(vm.tempero, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fastFoodDeliveryApp:temperoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
