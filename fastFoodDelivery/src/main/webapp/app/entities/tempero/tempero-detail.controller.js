(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('TemperoDetailController', TemperoDetailController);

    TemperoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tempero'];

    function TemperoDetailController($scope, $rootScope, $stateParams, previousState, entity, Tempero) {
        var vm = this;

        vm.tempero = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:temperoUpdate', function(event, result) {
            vm.tempero = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
