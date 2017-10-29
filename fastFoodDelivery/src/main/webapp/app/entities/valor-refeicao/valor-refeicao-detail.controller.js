(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('ValorRefeicaoDetailController', ValorRefeicaoDetailController);

    ValorRefeicaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ValorRefeicao'];

    function ValorRefeicaoDetailController($scope, $rootScope, $stateParams, previousState, entity, ValorRefeicao) {
        var vm = this;

        vm.valorRefeicao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:valorRefeicaoUpdate', function(event, result) {
            vm.valorRefeicao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
