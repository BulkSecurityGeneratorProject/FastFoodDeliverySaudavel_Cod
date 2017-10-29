(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('Tempero', Tempero);

    Tempero.$inject = ['$resource'];

    function Tempero ($resource) {
        var resourceUrl =  'api/temperos/:id';

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
