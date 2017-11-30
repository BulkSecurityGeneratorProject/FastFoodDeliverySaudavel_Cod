(function() {
    'use strict';
    angular
        .module('fastFoodDeliveryApp')
        .factory('Pessoa', Pessoa);

    Pessoa.$inject = ['$resource', 'DateUtils'];

    function Pessoa ($resource, DateUtils) {
        var resourceUrl =  'api/pessoas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataNascimento = DateUtils.convertLocalDateFromServer(data.dataNascimento);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataNascimento = DateUtils.convertLocalDateToServer(copy.dataNascimento);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataNascimento = DateUtils.convertLocalDateToServer(copy.dataNascimento);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
