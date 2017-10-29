(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('Cartao', Cartao);

    Cartao.$inject = ['$resource', 'DateUtils'];

    function Cartao ($resource, DateUtils) {
        var resourceUrl =  'api/cartaos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataVencimento = DateUtils.convertLocalDateFromServer(data.dataVencimento);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataVencimento = DateUtils.convertLocalDateToServer(copy.dataVencimento);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataVencimento = DateUtils.convertLocalDateToServer(copy.dataVencimento);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
