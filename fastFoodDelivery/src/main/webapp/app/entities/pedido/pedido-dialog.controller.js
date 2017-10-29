(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('PedidoDialogController', PedidoDialogController);

    PedidoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pedido', 'Bebida', 'FormaDeEntrega', 'Alimento'];

    function PedidoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pedido, Bebida, FormaDeEntrega, Alimento) {
        var vm = this;

        vm.pedido = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.bebidas = Bebida.query();
        vm.formadeentregas = FormaDeEntrega.query();
        vm.alimentos = Alimento.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pedido.id !== null) {
                Pedido.update(vm.pedido, onSaveSuccess, onSaveError);
            } else {
                Pedido.save(vm.pedido, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fastFoodDeliveryApp:pedidoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.horarioDeRetirada = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
