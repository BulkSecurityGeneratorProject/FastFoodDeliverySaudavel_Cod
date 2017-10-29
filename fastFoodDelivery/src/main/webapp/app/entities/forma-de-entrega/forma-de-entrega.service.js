(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('FormaDeEntrega', FormaDeEntrega);

    FormaDeEntrega.$inject = ['$resource'];

    function FormaDeEntrega ($resource) {
        var resourceUrl =  'api/forma-de-entregas/:id';

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
