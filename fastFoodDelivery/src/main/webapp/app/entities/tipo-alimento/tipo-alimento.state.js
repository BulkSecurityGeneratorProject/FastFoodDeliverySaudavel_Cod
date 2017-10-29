(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-alimento', {
            parent: 'entity',
            url: '/tipo-alimento?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.tipoAlimento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-alimento/tipo-alimentos.html',
                    controller: 'TipoAlimentoController',
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
                    $translatePartialLoader.addPart('tipoAlimento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipo-alimento-detail', {
            parent: 'tipo-alimento',
            url: '/tipo-alimento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.tipoAlimento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-alimento/tipo-alimento-detail.html',
                    controller: 'TipoAlimentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoAlimento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TipoAlimento', function($stateParams, TipoAlimento) {
                    return TipoAlimento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-alimento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-alimento-detail.edit', {
            parent: 'tipo-alimento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-alimento/tipo-alimento-dialog.html',
                    controller: 'TipoAlimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoAlimento', function(TipoAlimento) {
                            return TipoAlimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-alimento.new', {
            parent: 'tipo-alimento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-alimento/tipo-alimento-dialog.html',
                    controller: 'TipoAlimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tipoAlimento: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-alimento', null, { reload: 'tipo-alimento' });
                }, function() {
                    $state.go('tipo-alimento');
                });
            }]
        })
        .state('tipo-alimento.edit', {
            parent: 'tipo-alimento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-alimento/tipo-alimento-dialog.html',
                    controller: 'TipoAlimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoAlimento', function(TipoAlimento) {
                            return TipoAlimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-alimento', null, { reload: 'tipo-alimento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-alimento.delete', {
            parent: 'tipo-alimento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-alimento/tipo-alimento-delete-dialog.html',
                    controller: 'TipoAlimentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoAlimento', function(TipoAlimento) {
                            return TipoAlimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-alimento', null, { reload: 'tipo-alimento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
