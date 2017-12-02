(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('forma-de-entrega', {
            parent: 'entity',
            url: '/forma-de-entrega',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.formaDeEntrega.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/forma-de-entrega/forma-de-entregas.html',
                    controller: 'FormaDeEntregaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('formaDeEntrega');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('forma-de-entrega-detail', {
            parent: 'forma-de-entrega',
            url: '/forma-de-entrega/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.formaDeEntrega.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/forma-de-entrega/forma-de-entrega-detail.html',
                    controller: 'FormaDeEntregaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('formaDeEntrega');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FormaDeEntrega', function($stateParams, FormaDeEntrega) {
                    return FormaDeEntrega.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'forma-de-entrega',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('forma-de-entrega-detail.edit', {
            parent: 'forma-de-entrega-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forma-de-entrega/forma-de-entrega-dialog.html',
                    controller: 'FormaDeEntregaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FormaDeEntrega', function(FormaDeEntrega) {
                            return FormaDeEntrega.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('forma-de-entrega.new', {
            parent: 'forma-de-entrega',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forma-de-entrega/forma-de-entrega-dialog.html',
                    controller: 'FormaDeEntregaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descricao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('forma-de-entrega', null, { reload: 'forma-de-entrega' });
                }, function() {
                    $state.go('forma-de-entrega');
                });
            }]
        })
        .state('forma-de-entrega.edit', {
            parent: 'forma-de-entrega',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forma-de-entrega/forma-de-entrega-dialog.html',
                    controller: 'FormaDeEntregaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FormaDeEntrega', function(FormaDeEntrega) {
                            return FormaDeEntrega.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('forma-de-entrega', null, { reload: 'forma-de-entrega' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('forma-de-entrega.delete', {
            parent: 'forma-de-entrega',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forma-de-entrega/forma-de-entrega-delete-dialog.html',
                    controller: 'FormaDeEntregaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FormaDeEntrega', function(FormaDeEntrega) {
                            return FormaDeEntrega.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('forma-de-entrega', null, { reload: 'forma-de-entrega' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
