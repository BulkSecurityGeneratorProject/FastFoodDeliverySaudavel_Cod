(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('valor-nutricional', {
            parent: 'entity',
            url: '/valor-nutricional?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.valorNutricional.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valor-nutricional/valor-nutricionals.html',
                    controller: 'ValorNutricionalController',
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
                    $translatePartialLoader.addPart('valorNutricional');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('valor-nutricional-detail', {
            parent: 'valor-nutricional',
            url: '/valor-nutricional/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.valorNutricional.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valor-nutricional/valor-nutricional-detail.html',
                    controller: 'ValorNutricionalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('valorNutricional');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ValorNutricional', function($stateParams, ValorNutricional) {
                    return ValorNutricional.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'valor-nutricional',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('valor-nutricional-detail.edit', {
            parent: 'valor-nutricional-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valor-nutricional/valor-nutricional-dialog.html',
                    controller: 'ValorNutricionalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ValorNutricional', function(ValorNutricional) {
                            return ValorNutricional.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valor-nutricional.new', {
            parent: 'valor-nutricional',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valor-nutricional/valor-nutricional-dialog.html',
                    controller: 'ValorNutricionalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                caloria: null,
                                proteina: null,
                                carboidrato: null,
                                acucar: null,
                                gordurasSaturadas: null,
                                gordurasTotais: null,
                                sodio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('valor-nutricional', null, { reload: 'valor-nutricional' });
                }, function() {
                    $state.go('valor-nutricional');
                });
            }]
        })
        .state('valor-nutricional.edit', {
            parent: 'valor-nutricional',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valor-nutricional/valor-nutricional-dialog.html',
                    controller: 'ValorNutricionalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ValorNutricional', function(ValorNutricional) {
                            return ValorNutricional.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('valor-nutricional', null, { reload: 'valor-nutricional' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valor-nutricional.delete', {
            parent: 'valor-nutricional',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valor-nutricional/valor-nutricional-delete-dialog.html',
                    controller: 'ValorNutricionalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ValorNutricional', function(ValorNutricional) {
                            return ValorNutricional.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('valor-nutricional', null, { reload: 'valor-nutricional' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
