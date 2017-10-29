(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('PaisDetailController', PaisDetailController);

    PaisDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pais', 'Cartao'];

    function PaisDetailController($scope, $rootScope, $stateParams, previousState, entity, Pais, Cartao) {
        var vm = this;

        vm.pais = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:paisUpdate', function(event, result) {
            vm.pais = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
