(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('TipoAlimentoDetailController', TipoAlimentoDetailController);

    TipoAlimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoAlimento'];

    function TipoAlimentoDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoAlimento) {
        var vm = this;

        vm.tipoAlimento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:tipoAlimentoUpdate', function(event, result) {
            vm.tipoAlimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
