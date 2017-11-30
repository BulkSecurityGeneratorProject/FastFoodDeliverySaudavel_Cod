(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('RefeicaoDialogController', RefeicaoDialogController);

    RefeicaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Refeicao', 'ValorRefeicao', 'TipoAlimento'];

    function RefeicaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Refeicao, ValorRefeicao, TipoAlimento) {
        var vm = this;

        vm.refeicao = entity;
        vm.clear = clear;
        vm.save = save;
        vm.valorRefeicaos = ValorRefeicao.query();
        vm.tipoAlimentos = TipoAlimento.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.refeicao.id !== null) {
                Refeicao.update(vm.refeicao, onSaveSuccess, onSaveError);
            } else {
                Refeicao.save(vm.refeicao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fastFoodDeliveryApp:refeicaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
