(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('Bebida', Bebida);

    Bebida.$inject = ['$resource'];

    function Bebida ($resource) {
        var resourceUrl =  'api/bebidas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
