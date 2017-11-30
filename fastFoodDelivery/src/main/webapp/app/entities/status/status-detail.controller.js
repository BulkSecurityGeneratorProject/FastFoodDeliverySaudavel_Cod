(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('StatusDetailController', StatusDetailController);

    StatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Status', 'Pedido'];

    function StatusDetailController($scope, $rootScope, $stateParams, previousState, entity, Status, Pedido) {
        var vm = this;

        vm.status = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:statusUpdate', function(event, result) {
            vm.status = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
