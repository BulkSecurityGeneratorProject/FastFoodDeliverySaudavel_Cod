(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('Preparo', Preparo);

    Preparo.$inject = ['$resource'];

    function Preparo ($resource) {
        var resourceUrl =  'api/preparos/:id';

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
