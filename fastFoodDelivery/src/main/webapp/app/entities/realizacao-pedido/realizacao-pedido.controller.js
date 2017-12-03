(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('RealizacaoPedidoController', PedidoController);

    PedidoController.$inject = ['$state', 'Pedido', 'ParseLinks', 'AlertService', 'Refeicao', 'TipoAlimento', 'Alimento', 'Preparo', 'Tempero', 'Bebida', 'Cartao', 'Endereco', 'Doce'];

    function PedidoController($state, Pedido, ParseLinks, AlertService, Refeicao, TipoAlimento, Alimento, Preparo, Tempero, Bebida, Cartao, Endereco, Doce) {

        var vm = this;
        vm.selecionarRefeicao = selecionarRefeicao;
        vm.consultarAlimentos = consultarAlimentos;
        vm.adicionarAlimentoEscolhido = adicionarAlimentoEscolhido;
        vm.excluirAlimentoEscolhido = excluirAlimentoEscolhido;
        vm.adicionarBebidaEscolhida = adicionarBebidaEscolhida;
        vm.excluirBebidaEscolhida = excluirBebidaEscolhida;
        vm.enviarPedido = enviarPedido;

        // aba: refeicao
        vm.refeicoes = TipoAlimento.query();

        // aba: escolha de alimentos
//        vm.tiposAlimento = TipoAlimento.query();
        vm.preparos = Preparo.query();
        vm.temperos = Tempero.query();

        // aba: escolha de bebidas
        vm.bebidas = Bebida.query();
        vm.doces = Doce.query();

        // aba: pagamento e entrega
        vm.cartoes = Cartao.query();
        vm.enderecos = Endereco.query();
        vm.itensTotalizados = [];
        vm.totalValoresNutricionais = [];

        // geral
        vm.pedido = {
            alimentos: [],
            bebidas: []
        };

        vm.alimentoEscolhido = {};
        vm.tiposAlimento = [];
        
        function selecionarRefeicao(refeicao) {

            vm.refeicoes.forEach(function (refeicaoCadastrada) {

                if (refeicaoCadastrada.id !== refeicao.id) {
                    refeicaoCadastrada.selecionada = false;
                }

            });
            
            refeicao.selecionada = !refeicao.selecionada;

            // TODO ajustar cadastro de bebidas
            // vm.tiposAlimento = refeicao.tiposAlimento;

            vm.alimentoEscolhido.refeicao = refeicao;
            
            vm.alimentoEscolhido.tipoAlimento = refeicao;
            
            vm.tiposAlimento.push(refeicao);
            
            consultarAlimentos();

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

            vm.pedido.alimentos.push(vm.alimentoEscolhido);

            vm.alimentoEscolhido = {};

            atualizarItensTotalizados();

            atualizarValoresNutricionais();

        }

        function excluirAlimentoEscolhido(index) {
            vm.pedido.alimentos.splice(index, 1);
        }

        function adicionarBebidaEscolhida() {

            vm.pedido.bebidas.push(vm.alimentoEscolhido.bebida);

            vm.alimentoEscolhido.bebida = {};

            atualizarItensTotalizados();

            atualizarValoresNutricionais();

        }

        function excluirBebidaEscolhida(index) {
            vm.pedido.bebidas.splice(index, 1);
        }
        
        function atualizarItensTotalizados() {

            vm.itensTotalizados = [];

            var totalAlimentos = 0;
            var totalBebidas = 0;

            var item = {};
            item.nome = 'Alimentos';
            item.quantidade = vm.pedido.alimentos.length;
            item.total = totalAlimentos;

            vm.itensTotalizados.push(item);

            vm.bebidas.forEach(function (bebida) {

                var valorBebida = bebida.valorRefeicao.valor / bebida.capacidade;

                totalBebidas += valorBebida;

            });

            var item = {};
            item.nome = 'Bebidas';
            item.quantidade = vm.pedido.bebidas.length;
            item.total = totalBebidas;

            vm.itensTotalizados.push(item);

        }

        function atualizarValoresNutricionais() {

            var valorNutricionalTotalizado = {
                totalCalorias: 0,
                totalProteinas: 0,
                totalCarboidratos: 0,
                totalAcucares: 0,
                totalGordurasTotais: 0,
                totalGordurasSaturadas: 0,
                totalSodio: 0
            };

            vm.pedido.alimentos.forEach(function (alimentoSelecionado) {

                var valorNutricional = alimentoSelecionado.alimento.valorNutricional;

                var caloria = alimentoSelecionado.preparo.tempoPreparo * valorNutricional.caloria;

                somarValorNutrucional(valorNutricionalTotalizado, valorNutricional, caloria);

            });

            vm.pedido.bebidas.forEach(function (bebidaSelecionada) {

                var valorNutricional = bebidaSelecionada.valorNutricional;

                var caloria = valorNutricional.caloria;

                somarValorNutrucional(valorNutricionalTotalizado, valorNutricional, caloria);

            });

            vm.totalValoresNutricionais = [];
            vm.totalValoresNutricionais.push(valorNutricionalTotalizado);

        }

        function somarValorNutrucional(valorNutricionalTotalizado, valorNutricional, caloria) {

            valorNutricionalTotalizado.totalCalorias += caloria;
            valorNutricionalTotalizado.totalProteinas += valorNutricional.proteina;
            valorNutricionalTotalizado.totalCarboidratos += valorNutricional.carboidrato;
            valorNutricionalTotalizado.totalAcucares += valorNutricional.acucar;
            valorNutricionalTotalizado.totalGordurasTotais += valorNutricional.gordurasTotais;
            valorNutricionalTotalizado.totalGordurasSaturadas += valorNutricional.gordurasSaturadas;
            valorNutricionalTotalizado.totalSodio += valorNutricional.sodio;

            return valorNutricionalTotalizado;

        }

        function enviarPedido () {

            vm.isSaving = true;

            Pedido.save(vm.pedido, onSaveSuccess, onSaveError);

        }

        function onSaveSuccess (result) {

            vm.isSaving = false;

            $state.go('sucesso-realizacao-pedido');

        }

        function onSaveError () {
            vm.isSaving = false;
        }

    }
})();
