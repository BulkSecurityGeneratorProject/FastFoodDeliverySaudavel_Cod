(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('AlimentoDialogController', AlimentoDialogController);

    AlimentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Alimento', 'Preparo', 'Tempero', 'ValorNutricional', 'TipoAlimento'];

    function AlimentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Alimento, Preparo, Tempero, ValorNutricional, TipoAlimento) {
        var vm = this;

        vm.alimento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.preparos = Preparo.query();
        vm.temperos = Tempero.query();
        vm.valornutricionals = ValorNutricional.query({filter: 'alimento-is-null'});
        $q.all([vm.alimento.$promise, vm.valornutricionals.$promise]).then(function() {
            if (!vm.alimento.valorNutricional || !vm.alimento.valorNutricional.id) {
                return $q.reject();
            }
            return ValorNutricional.get({id : vm.alimento.valorNutricional.id}).$promise;
        }).then(function(valorNutricional) {
            vm.valornutricionals.push(valorNutricional);
        });
        vm.tipoalimentos = TipoAlimento.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.alimento.id !== null) {
                Alimento.update(vm.alimento, onSaveSuccess, onSaveError);
            } else {
                Alimento.save(vm.alimento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fastFoodDeliveryApp:alimentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
