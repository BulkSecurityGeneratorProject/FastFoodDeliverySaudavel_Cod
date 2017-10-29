(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('TipoAlimento', TipoAlimento);

    TipoAlimento.$inject = ['$resource'];

    function TipoAlimento ($resource) {
        var resourceUrl =  'api/tipo-alimentos/:id';

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
