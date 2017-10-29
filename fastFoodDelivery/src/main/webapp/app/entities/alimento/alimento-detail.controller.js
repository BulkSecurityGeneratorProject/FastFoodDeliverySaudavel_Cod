(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('AlimentoDetailController', AlimentoDetailController);

    AlimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Alimento', 'Preparo', 'Tempero', 'ValorNutricional', 'TipoAlimento'];

    function AlimentoDetailController($scope, $rootScope, $stateParams, previousState, entity, Alimento, Preparo, Tempero, ValorNutricional, TipoAlimento) {
        var vm = this;

        vm.alimento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:alimentoUpdate', function(event, result) {
            vm.alimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
