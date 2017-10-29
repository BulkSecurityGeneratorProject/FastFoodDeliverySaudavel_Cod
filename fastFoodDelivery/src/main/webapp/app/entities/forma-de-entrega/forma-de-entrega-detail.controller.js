(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('FormaDeEntregaDetailController', FormaDeEntregaDetailController);

    FormaDeEntregaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FormaDeEntrega'];

    function FormaDeEntregaDetailController($scope, $rootScope, $stateParams, previousState, entity, FormaDeEntrega) {
        var vm = this;

        vm.formaDeEntrega = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:formaDeEntregaUpdate', function(event, result) {
            vm.formaDeEntrega = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
