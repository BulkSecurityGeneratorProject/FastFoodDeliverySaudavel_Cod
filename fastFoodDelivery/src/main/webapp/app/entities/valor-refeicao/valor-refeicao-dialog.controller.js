(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('ValorRefeicaoDialogController', ValorRefeicaoDialogController);

    ValorRefeicaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ValorRefeicao'];

    function ValorRefeicaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ValorRefeicao) {
        var vm = this;

        vm.valorRefeicao = entity;
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
            if (vm.valorRefeicao.id !== null) {
                ValorRefeicao.update(vm.valorRefeicao, onSaveSuccess, onSaveError);
            } else {
                ValorRefeicao.save(vm.valorRefeicao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fastFoodDeliveryApp:valorRefeicaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
