(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('Pedido', Pedido);

    Pedido.$inject = ['$resource', 'DateUtils'];

    function Pedido ($resource, DateUtils) {
        var resourceUrl =  'api/pedidos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.horarioDeRetirada = DateUtils.convertDateTimeFromServer(data.horarioDeRetirada);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
