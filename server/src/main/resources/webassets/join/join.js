var base = base || {};
base.joinRideController = function() {
    var matchedRides = [];

    var view = {
        render: function () {
            matchedRides.forEach(ride => view.renderRow(ride));
        },
        renderRow: function(ride) {
            var tbody = document.getElementById('join-table');
            var tr = document.createElement("tr");

            var td1 = document.createElement("td");
            var txt1 = document.createTextNode(ride.departureLocation);
            td1.appendChild(txt1);
            tr.appendChild(td1);

            var td2 = document.createElement("td");
            var txt2 = document.createTextNode(ride.arrivalLocation);
            td2.appendChild(txt2);
            tr.appendChild(td2);

            var td3 = document.createElement("td");
            var txt3 = document.createTextNode(ride.departureTime.substring(0,16));
            td3.appendChild(txt3);
            tr.appendChild(td3);

            var td4 = document.createElement("td");
            var txt4 = document.createTextNode(ride.arrivalTime.substring(0,16));
            td4.appendChild(txt4);
            tr.appendChild(td4);

            var td5 = document.createElement("td");
            var txt5 = document.createTextNode(ride.carSize);
            td5.appendChild(txt5);
            tr.appendChild(td5);


            var secondlasttd = view.createDropDown();

            tr.appendChild(secondlasttd);

            var lasttd = view.createJoinBtn(ride);

            tr.appendChild(lasttd);

            tbody.appendChild(tr);
        },
        createDropDown: function () {
            var secondlasttd = document.createElement("td");
            var dropdowndiv = document.createElement("div");
            dropdowndiv.className ="dropdown";
            var dropdownbtn = document.createElement("button");
            dropdownbtn.classList = "btn btn-info dropdown-toggle";
            dropdownbtn.type = "button";
            $(".dropdown-toggle").attr("data-toggle", "dropdown");
            dropdownbtn.ariaHaspopup="true";
            dropdownbtn.ariaExpanded="true";
            dropdownbtn.textContent = "Ride participants";
            dropdowndiv.appendChild(dropdownbtn);
            var menudiv = document.createElement("div");
            menudiv.className= "dropdown-menu";

            var users = ['Anders (Driver)', 'Gertrud', 'Göran'];

            for(var i = 0; i < users.length ; i++) {
                var h6 = document.createElement("h6");
                h6.className = "dropdown-item";
                h6.textContent = users[i];
                menudiv.appendChild(h6);
            }

            dropdowndiv.appendChild(menudiv);
            secondlasttd.appendChild(dropdowndiv);

            return secondlasttd;
        },
        createJoinBtn: function (ride) {
            var lasttd = document.createElement("td");
            lasttd.id = 'last-td';
            var btn = document.createElement("button");
            btn.className = "btn btn-success";
            btn.textContent = "Join";
            lasttd.style.background = '#f5f5f5';
            lasttd.style.borderTop = 'none';

            btn.onclick = function(event) {
                    // if(base.rest.isBusy(user.id, from, to) alert('You are already scheduled in tnis time interval')
                    // else joinRide(user.id, ride.id); alert('Ride successfully joined');
                    base.rest.joinRide(ride.id);
                alert('Ride successfully joined');
            };

            lasttd.appendChild(btn);

            return lasttd;
        }
    };

    var controller = {
        load: function () {
            var select_from = document.getElementById("select-from");
            var select_to = document.getElementById("select-to");

            base.rest.getLocations().then(function(locations) {
                locations.forEach(function(location, index) {
                    select_to.options[select_to.options.length] = new Option(location.name, index);
                    select_from.options[select_from.options.length] = new Option(location.name, index);
                });
            });

            base.rest.getRides().then(function(rides){
                console.log(rides);
                matchedRides = rides;
                return matchedRides;
            });

            document.getElementById("search-rides").onclick = function (event){
            document.getElementById("matched-rides").style.visibility = "visible";
            view.render();
            }
        }
    };
    return controller;
};