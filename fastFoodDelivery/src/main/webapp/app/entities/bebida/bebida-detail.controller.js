(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('BebidaDetailController', BebidaDetailController);

    BebidaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Bebida', 'Doce', 'ValorNutricional', 'ValorRefeicao'];

    function BebidaDetailController($scope, $rootScope, $stateParams, previousState, entity, Bebida, Doce, ValorNutricional, ValorRefeicao) {
        var vm = this;

        vm.bebida = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:bebidaUpdate', function(event, result) {
            vm.bebida = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
