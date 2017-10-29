(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('ValorNutricionalDetailController', ValorNutricionalDetailController);

    ValorNutricionalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ValorNutricional'];

    function ValorNutricionalDetailController($scope, $rootScope, $stateParams, previousState, entity, ValorNutricional) {
        var vm = this;

        vm.valorNutricional = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:valorNutricionalUpdate', function(event, result) {
            vm.valorNutricional = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
