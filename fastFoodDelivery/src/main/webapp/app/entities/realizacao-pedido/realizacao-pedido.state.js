(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('realizacao-pedido', {
            parent: 'entity',
            url: '/realizar-pedido',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.pedido.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/realizacao-pedido/realizacao-pedido.html',
                    controller: 'RealizacaoPedidoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pedido');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sucesso-realizacao-pedido', {
            parent: 'entity',
            url: '/sucesso-cadastro-pedido',
            data: {
                authorities: [],
                pageTitle: 'Sucesso no Cadastro da Pedido'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/realizacao-pedido/sucesso-realizacao-pedido.html',
                    controller: 'SucessoRealizacaoPedidoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }

})();
