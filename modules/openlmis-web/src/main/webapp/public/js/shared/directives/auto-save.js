/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

app.directive('autoSave', function ($route, IndexedDB, $timeout) {
  return {
    restrict: 'A',
    link: function (scope, element, attrs) {

      var save = function () {
        IndexedDB.put(attrs.objectStore, scope[attrs.autoSave]);
      };

      $timeout(function () {
        element.find('input, textarea').bind('blur', save);
        element.find(':radio, :checkbox').bind('click', function () {
          this.focus();   //like a boss- btw it is a hack to make this work in webkit
        });
      });
    }
  };
});

