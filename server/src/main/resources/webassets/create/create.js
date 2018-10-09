var base = base || {};
base.createRideController = function() {
    var controller = {
        load: function () {
            document.getElementById('create').onclick = function(event) {
                var nbr_seats = document.getElementById("create-seats").value;
                var from = document.getElementById("create-from").value;
                var to = document.getElementById("create-to").value;
                var arr_date_time = document.getElementById("create-arr-time").value;
                var dep_date_time = document.getElementById("create-dep-time").value;
                
                var today = controller.getTodaysDate();

                var arr_hrs_mins = controller.getTimeFromInput(arr_date_time);
                var dep_hrs_mins = controller.getTimeFromInput(dep_date_time);

                var arr_date = controller.getDateFromInput(arr_date_time);
                var dep_date = controller.getDateFromInput(dep_date_time);

                var correct_dep_date = dep_date + ' ' + dep_hrs_mins;
                var correct_arr_date = arr_date + ' ' + arr_hrs_mins;

                // controller.clearInputFields();

                var isIllegalInput = controller.isIncorrectInformation(nbr_seats, to, from, arr_date_time, dep_date_time);
                var hh = new Date().getHours();
                var mm = new Date().getMinutes();

                if(hh < 10) hh = '0' + hh;
                if(mm < 10) mm = '0' + mm;

                var isPassedDate = controller.isPassedDate(dep_date, arr_date, today, hh, mm, dep_hrs_mins)

                // Call method that creates a ride in the database
                // Check if any of the input values are invalid
                // Add check for from and to not in location list
                if(isIllegalInput){
                    alert('The information entered was not correct.');
                } else if(isPassedDate){
                    alert('Departure and/or arrival time has already passed');
                } else if (correct_arr_date <= correct_dep_date) {
                    alert('Arrival time needs to occur before the departure time');
                } else {
                    alert("Ride successfully created");
                }
                return false;
            };
        },
        isIncorrectInformation: function (seats, to, from, arr_date, dep_date) {
            var locationRegex = /^[\x20-\x7EåäöÅÄÖ]+$/
    
            var fromOkay = from.match(locationRegex);
            var toOkay = to.match(locationRegex);
    
            return isNaN(seats) || seats <= 0 || !fromOkay || !toOkay ||
            arr_date.length > 16 || dep_date.length > 16
        },
        getTodaysDate: function () {
            var today = new Date();
            var dd = today.getDate();
            var Mm = today.getMonth()+1; //January is 0!
            var yyyy = today.getFullYear();
            var hh = today.getHours();
            var mm = today.getMinutes();
    
            if(dd < 10) dd = '0' + dd;
            if(Mm < 10) Mm = '0' + Mm;
            if(hh < 10) hh = '0' + hh;
            if(mm < 10) mm = '0' + mm;
    
            return yyyy + '-' + Mm + '-' + dd;
        },
        getDateFromInput: function (date_and_time) {
            return date_and_time.substring(0,10);
        },
        getTimeFromInput: function (date_and_time) {
            return date_and_time.substring(11,16);
        },
        clearInputFields: function () {
            document.getElementById("create-seats").value = "";
            document.getElementById("create-from").value = "";
            document.getElementById("create-to").value = "";
            document.getElementById("create-arr-time").value = "";
            document.getElementById("create-dep-time").value = "";
            document.getElementById('username').textContent = "";
        },
        isPassedDate: function (dep_date, arr_date, today, hh, mm, dep_time) {
            return dep_date < today || arr_date < today || ((dep_date === today) && dep_time < (hh + ':' + mm));
        }
    };
    return controller;
};