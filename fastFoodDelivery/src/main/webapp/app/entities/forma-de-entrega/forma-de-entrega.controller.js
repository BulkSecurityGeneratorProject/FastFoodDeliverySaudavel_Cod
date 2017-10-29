(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('FormaDeEntregaController', FormaDeEntregaController);

    FormaDeEntregaController.$inject = ['FormaDeEntrega'];

    function FormaDeEntregaController(FormaDeEntrega) {

        var vm = this;

        vm.formaDeEntregas = [];

        loadAll();

        function loadAll() {
            FormaDeEntrega.query(function(result) {
                vm.formaDeEntregas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
