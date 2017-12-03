(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('AcompanharPedidoController', PedidoController);

    PedidoController.$inject = ['$state', 'Pedido', 'Principal'];

    function PedidoController($state, Pedido, Principal) {

        var vm = this;
        
        vm.settingsAccount = null;
        vm.pedidos = null;
        
        var copyAccount = function (account) {
            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login,
                id: account.id
            };
        };

        Principal.identity().then(function(account) {
        	console.log(account);
            vm.settingsAccount = copyAccount(account);
            Pedido.getAllPedidosByUsuario({idUsuario: vm.settingsAccount.id}, onPesquisaPedidoSuccess, onPesquisaPedidoError);
        });
        
        function onPesquisaPedidoSuccess(data, headers) {
            vm.pedidos = data;
        }

        function onPesquisaPedidoError(error) {
            AlertService.error("Não foi possível pesquisar os pedidos. Aguarde alguns instantes ou entre em contato com o nosso suporte.");
        }

    }
})();
