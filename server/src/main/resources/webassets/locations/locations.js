var base = base || {};
base.locationsController = function() {
    var locationList = [
        'Malmö', 'Lund', 'Stockholm', 'Göteborg', 'Malmö', 'Lund', 'Stockholm', 'Göteborg', 'Malmö', 'Lund', 'Stockholm', 'Göteborg', 'Malmö', 'Lund', 'Stockholm', 'Göteborg', 'Malmö', 'Lund', 'Stockholm', 'Göteborg', 'Malmö', 'Lund', 'Stockholm', 'Göteborg' , 'Malmö', 'Lund', 'Stockholm', 'Göteborg', 'Malmö', 'Lund', 'Stockholm', 'Göteborg', 'Malmö', 'Lund', 'Stockholm', 'Göteborg', 'Malmö', 'Lund', 'Stockholm', 'Göteborg', 'Malmö', 'Lund', 'Stockholm', 'Göteborg', 'Malmö', 'Lund', 'Stockholm', 'Göteborg', 'Malmö', 'Lund', 'Stockholm', 'Göteborg', 'Malmö', 'Lund', 'Stockholm', 'Göteborg'
    ];
    var controller = {
        load: function () {
            var ol = document.getElementById("locations-list");

            for(var i = 0; i < locationList.length; i++) {
                var li = document.createElement("li");
                li.textContent = locationList[i];
                ol.appendChild(li);
            }
        }
    }
    return controller;
};