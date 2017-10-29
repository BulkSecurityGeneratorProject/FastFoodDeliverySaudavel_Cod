(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('CartaoDetailController', CartaoDetailController);

    CartaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cartao'];

    function CartaoDetailController($scope, $rootScope, $stateParams, previousState, entity, Cartao) {
        var vm = this;

        vm.cartao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:cartaoUpdate', function(event, result) {
            vm.cartao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
