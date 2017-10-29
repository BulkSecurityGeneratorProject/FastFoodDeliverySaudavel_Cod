(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('DoceDetailController', DoceDetailController);

    DoceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Doce'];

    function DoceDetailController($scope, $rootScope, $stateParams, previousState, entity, Doce) {
        var vm = this;

        vm.doce = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:doceUpdate', function(event, result) {
            vm.doce = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
