(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('Doce', Doce);

    Doce.$inject = ['$resource'];

    function Doce ($resource) {
        var resourceUrl =  'api/doces/:id';

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
