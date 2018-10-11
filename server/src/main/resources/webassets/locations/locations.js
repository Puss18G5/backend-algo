var base = base || {};
base.locationsController = function() {
    var controller = {
        load: function () {
            var ol = document.getElementById("locations-list");

            base.rest.getLocations().then(function(locations) {
                locations.forEach(function(location) {
                    var li = document.createElement("li");
                    li.textContent = location.name;
                    ol.appendChild(li);
                })
            });
        }
    }
    return controller;
};