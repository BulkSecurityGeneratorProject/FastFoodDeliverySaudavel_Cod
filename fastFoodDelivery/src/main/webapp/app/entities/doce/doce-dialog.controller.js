(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('DoceDialogController', DoceDialogController);

    DoceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Doce'];

    function DoceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Doce) {
        var vm = this;

        vm.doce = entity;
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
            if (vm.doce.id !== null) {
                Doce.update(vm.doce, onSaveSuccess, onSaveError);
            } else {
                Doce.save(vm.doce, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fastFoodDeliveryApp:doceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
