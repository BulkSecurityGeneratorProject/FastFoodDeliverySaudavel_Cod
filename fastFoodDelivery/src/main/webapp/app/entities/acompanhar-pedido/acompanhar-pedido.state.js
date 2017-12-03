(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('acompanhar-pedido', {
            parent: 'entity',
            url: '/acompanhar-pedido',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.pedido.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/acompanhar-pedido/acompanhar-pedido.html',
                    controller: 'AcompanharPedidoController',
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
    }
    
})();
