(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('ValorRefeicao', ValorRefeicao);

    ValorRefeicao.$inject = ['$resource'];

    function ValorRefeicao ($resource) {
        var resourceUrl =  'api/valor-refeicaos/:id';

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
