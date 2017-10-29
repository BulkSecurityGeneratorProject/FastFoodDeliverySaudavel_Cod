(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('PreparoDetailController', PreparoDetailController);

    PreparoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Preparo'];

    function PreparoDetailController($scope, $rootScope, $stateParams, previousState, entity, Preparo) {
        var vm = this;

        vm.preparo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fastFoodDeliveryApp:preparoUpdate', function(event, result) {
            vm.preparo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
