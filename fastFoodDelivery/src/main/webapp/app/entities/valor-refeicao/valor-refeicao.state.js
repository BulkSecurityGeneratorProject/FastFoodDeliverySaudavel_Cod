(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('valor-refeicao', {
            parent: 'entity',
            url: '/valor-refeicao?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.valorRefeicao.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valor-refeicao/valor-refeicaos.html',
                    controller: 'ValorRefeicaoController',
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
                    $translatePartialLoader.addPart('valorRefeicao');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('valor-refeicao-detail', {
            parent: 'valor-refeicao',
            url: '/valor-refeicao/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.valorRefeicao.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valor-refeicao/valor-refeicao-detail.html',
                    controller: 'ValorRefeicaoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('valorRefeicao');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ValorRefeicao', function($stateParams, ValorRefeicao) {
                    return ValorRefeicao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'valor-refeicao',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('valor-refeicao-detail.edit', {
            parent: 'valor-refeicao-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valor-refeicao/valor-refeicao-dialog.html',
                    controller: 'ValorRefeicaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ValorRefeicao', function(ValorRefeicao) {
                            return ValorRefeicao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valor-refeicao.new', {
            parent: 'valor-refeicao',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valor-refeicao/valor-refeicao-dialog.html',
                    controller: 'ValorRefeicaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                valor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('valor-refeicao', null, { reload: 'valor-refeicao' });
                }, function() {
                    $state.go('valor-refeicao');
                });
            }]
        })
        .state('valor-refeicao.edit', {
            parent: 'valor-refeicao',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valor-refeicao/valor-refeicao-dialog.html',
                    controller: 'ValorRefeicaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ValorRefeicao', function(ValorRefeicao) {
                            return ValorRefeicao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('valor-refeicao', null, { reload: 'valor-refeicao' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valor-refeicao.delete', {
            parent: 'valor-refeicao',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valor-refeicao/valor-refeicao-delete-dialog.html',
                    controller: 'ValorRefeicaoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ValorRefeicao', function(ValorRefeicao) {
                            return ValorRefeicao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('valor-refeicao', null, { reload: 'valor-refeicao' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
