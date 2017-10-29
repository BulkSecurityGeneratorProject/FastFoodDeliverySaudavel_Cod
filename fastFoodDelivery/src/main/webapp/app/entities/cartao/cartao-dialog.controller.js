(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('CartaoDialogController', CartaoDialogController);

    CartaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cartao'];

    function CartaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cartao) {
        var vm = this;

        vm.cartao = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cartao.id !== null) {
                Cartao.update(vm.cartao, onSaveSuccess, onSaveError);
            } else {
                Cartao.save(vm.cartao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fastFoodDeliveryApp:cartaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataVencimento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
