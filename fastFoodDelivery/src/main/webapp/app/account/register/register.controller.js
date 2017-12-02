(function() {
    'use strict';

    angular
        .module('fastFoodDeliveryApp')
        .controller('RegisterController', RegisterController);


    RegisterController.$inject = ['$translate', '$timeout', 'Auth', 'LoginService', 'errorConstants'];

    function RegisterController ($translate, $timeout, Auth, LoginService, errorConstants) {
        var vm = this;

        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.openCalendar = openCalendar;
        vm.datePickerOpenStatus = {};
        vm.registerAccount = {
        		email: 'teste@gmail.com',
        		password: '123456'
        };
        vm.confirmPassword = '123456';
        vm.success = null;

        $timeout(function (){angular.element('#login').focus();});
        
        function register () {
            if (vm.registerAccount.password !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                vm.registerAccount.langKey = $translate.use();
                vm.doNotMatch = null;
                vm.error = null;
                vm.errorUserExists = null;
                vm.errorEmailExists = null;

                Auth.createAccount(vm.registerAccount).then(function () {
                    vm.success = 'OK';
                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 400 && angular.fromJson(response.data).type === errorConstants.LOGIN_ALREADY_USED_TYPE) {
                        vm.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && angular.fromJson(response.data).type === errorConstants.EMAIL_ALREADY_USED_TYPE) {
                        vm.errorEmailExists = 'ERROR';
                    } else {
                        vm.error = 'ERROR';
                    }
                });
            }
        }
        
        vm.datePickerOpenStatus.datanascimento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
