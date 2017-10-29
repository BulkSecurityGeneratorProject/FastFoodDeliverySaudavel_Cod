(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('PreparoDialogController', PreparoDialogController);

    PreparoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Preparo'];

    function PreparoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Preparo) {
        var vm = this;

        vm.preparo = entity;
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
            if (vm.preparo.id !== null) {
                Preparo.update(vm.preparo, onSaveSuccess, onSaveError);
            } else {
                Preparo.save(vm.preparo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fastFoodDeliveryApp:preparoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
