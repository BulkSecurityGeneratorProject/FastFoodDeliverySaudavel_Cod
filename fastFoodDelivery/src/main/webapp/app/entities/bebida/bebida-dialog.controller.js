(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('BebidaDialogController', BebidaDialogController);

    BebidaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Bebida', 'Doce', 'ValorNutricional', 'ValorRefeicao'];

    function BebidaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Bebida, Doce, ValorNutricional, ValorRefeicao) {
        var vm = this;

        vm.bebida = entity;
        vm.clear = clear;
        vm.save = save;
        vm.doces = Doce.query();
        vm.valornutricionals = ValorNutricional.query({filter: 'bebida-is-null'});
        $q.all([vm.bebida.$promise, vm.valornutricionals.$promise]).then(function() {
            if (!vm.bebida.valorNutricional || !vm.bebida.valorNutricional.id) {
                return $q.reject();
            }
            return ValorNutricional.get({id : vm.bebida.valorNutricional.id}).$promise;
        }).then(function(valorNutricional) {
            vm.valornutricionals.push(valorNutricional);
        });
        vm.valorrefeicaos = ValorRefeicao.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bebida.id !== null) {
                Bebida.update(vm.bebida, onSaveSuccess, onSaveError);
            } else {
                Bebida.save(vm.bebida, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fastFoodDeliveryApp:bebidaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
