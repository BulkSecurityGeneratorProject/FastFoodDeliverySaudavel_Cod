(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('RealizacaoPedidoController', PedidoController);

    PedidoController.$inject = ['$state', 'Pedido', 'ParseLinks', 'AlertService', 'Refeicao', 'TipoAlimento', 'Alimento', 'Preparo', 'Tempero'];

    function PedidoController($state, Pedido, ParseLinks, AlertService, Refeicao, TipoAlimento, Alimento, Preparo, Tempero) {

        var vm = this;
        vm.selecionarRefeicao = selecionarRefeicao;
        vm.consultarAlimentos = consultarAlimentos;
        vm.adicionarAlimentoEscolhido = adicionarAlimentoEscolhido;
        vm.excluirAlimentoEscolhido = excluirAlimentoEscolhido;

        vm.refeicoes = Refeicao.query();
        vm.tiposAlimento = TipoAlimento.query();
        vm.preparos = Preparo.query();
        vm.temperos = Tempero.query();
        vm.alimentoEscolhido = {};
        vm.alimentosEscolhidos = [];

        loadAll();

        function loadAll () {

        }

        function selecionarRefeicao(refeicao) {
            refeicao.selecionada = !refeicao.selecionada;
        }

        function consultarAlimentos() {

            if (!vm.alimentoEscolhido.tipoAlimento) {
                return;
            }

            Alimento.getAllAlimentosByTipoAlimento({idTipoAlimento: vm.alimentoEscolhido.tipoAlimento.id}, onPesquisaAlimentoSuccess, onPesquisaAlimentoError);

        }

        function onPesquisaAlimentoSuccess(data, headers) {
            vm.alimentos = data;
        }

        function onPesquisaAlimentoError(error) {
            AlertService.error("Não foi possível pesquisar os tipos de alimento. Aguarde alguns instantes ou entre em contato com o nosso suporte.");
        }

        function adicionarAlimentoEscolhido() {

            vm.alimentosEscolhidos.push(vm.alimentoEscolhido);

            vm.alimentoEscolhido = {};

        }

        function excluirAlimentoEscolhido(index) {
            vm.alimentosEscolhidos.splice(index, 1);
        }

    }
})();
