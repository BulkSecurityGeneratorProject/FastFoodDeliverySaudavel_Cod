(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cartao', {
            parent: 'entity',
            url: '/cartao?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.cartao.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cartao/cartaos.html',
                    controller: 'CartaoController',
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
                    $translatePartialLoader.addPart('cartao');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cartao-detail', {
            parent: 'cartao',
            url: '/cartao/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'fastFoodDeliveryApp.cartao.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cartao/cartao-detail.html',
                    controller: 'CartaoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cartao');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cartao', function($stateParams, Cartao) {
                    return Cartao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cartao',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cartao-detail.edit', {
            parent: 'cartao-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cartao/cartao-dialog.html',
                    controller: 'CartaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cartao', function(Cartao) {
                            return Cartao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cartao.new', {
            parent: 'cartao',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cartao/cartao-dialog.html',
                    controller: 'CartaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero: null,
                                dataVencimento: null,
                                cvv: null,
                                cartaoCol: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cartao', null, { reload: 'cartao' });
                }, function() {
                    $state.go('cartao');
                });
            }]
        })
        .state('cartao.realizacao-pedido', {
        	parent: 'realizacao-pedido',
        	url: '/cartao/new',
        	data: {
        		authorities: ['ROLE_USER']
        	},
        	onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        		$uibModal.open({
        			templateUrl: 'app/entities/cartao/cartao-dialog.html',
        			controller: 'CartaoDialogController',
        			controllerAs: 'vm',
        			backdrop: 'static',
        			size: 'lg',
        			resolve: {
        				entity: function () {
        					return {
        						numero: null,
        						dataVencimento: null,
        						cvv: null,
        						cartaoCol: null,
        						id: null
        					};
        				}
        			}
        		}).result.then(function() {
        			$state.go('realizacao-pedido', null, { reload: '' });
        		}, function() {
        			$state.go('realizacao-pedido');
        		});
        	}]
        })
        .state('cartao.edit', {
            parent: 'cartao',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cartao/cartao-dialog.html',
                    controller: 'CartaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cartao', function(Cartao) {
                            return Cartao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cartao', null, { reload: 'cartao' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cartao.delete', {
            parent: 'cartao',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cartao/cartao-delete-dialog.html',
                    controller: 'CartaoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cartao', function(Cartao) {
                            return Cartao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cartao', null, { reload: 'cartao' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
