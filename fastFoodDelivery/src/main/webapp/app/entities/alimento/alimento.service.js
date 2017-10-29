(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('Alimento', Alimento);

    Alimento.$inject = ['$resource'];

    function Alimento ($resource) {
        var resourceUrl =  'api/alimentos/:id';

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
