(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cliente', {
            parent: 'entity',
            url: '/cliente?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.cliente.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cliente/clientes.html',
                    controller: 'ClienteController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cliente');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cliente-detail', {
            parent: 'cliente',
            url: '/cliente/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.cliente.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cliente/cliente-detail.html',
                    controller: 'ClienteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cliente');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cliente', function($stateParams, Cliente) {
                    return Cliente.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cliente',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cliente-detail.edit', {
            parent: 'cliente-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cliente/cliente-dialog.html',
                    controller: 'ClienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cliente', function(Cliente) {
                            return Cliente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cliente.new', {
            parent: 'cliente',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cliente/cliente-dialog.html',
                    controller: 'ClienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                peso: null,
                                altura: null,
                                calorias: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cliente', null, { reload: 'cliente' });
                }, function() {
                    $state.go('cliente');
                });
            }]
        })
        .state('cliente.edit', {
            parent: 'cliente',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cliente/cliente-dialog.html',
                    controller: 'ClienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cliente', function(Cliente) {
                            return Cliente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cliente', null, { reload: 'cliente' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cliente.delete', {
            parent: 'cliente',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cliente/cliente-delete-dialog.html',
                    controller: 'ClienteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cliente', function(Cliente) {
                            return Cliente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cliente', null, { reload: 'cliente' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
