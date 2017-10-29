(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('RefeicaoDetailController', RefeicaoDetailController);

    RefeicaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Refeicao', 'ValorRefeicao', 'TipoAlimento'];

    function RefeicaoDetailController($scope, $rootScope, $stateParams, previousState, entity, Refeicao, ValorRefeicao, TipoAlimento) {
        var vm = this;

        vm.refeicao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:refeicaoUpdate', function(event, result) {
            vm.refeicao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
