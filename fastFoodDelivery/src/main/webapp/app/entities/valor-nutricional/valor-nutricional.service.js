(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('ValorNutricional', ValorNutricional);

    ValorNutricional.$inject = ['$resource'];

    function ValorNutricional ($resource) {
        var resourceUrl =  'api/valor-nutricionals/:id';

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
