(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('Refeicao', Refeicao);

    Refeicao.$inject = ['$resource'];

    function Refeicao ($resource) {
        var resourceUrl =  'api/refeicaos/:id';

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
