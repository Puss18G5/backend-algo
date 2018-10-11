var base = base || {};
base.joinRideController = function() {
    var matchedRides = [
        {
        dep_loc: 'Malmö',
        arr_loc: 'Lund',
        dep_time: '2018-09-10 11:03',
        arr_time: '2018-09-10 12:03',
        seats: 4
        },
        {
        dep_loc: 'Lund',
        arr_loc: 'Malmö',
        dep_time: '2018-09-10 15:04',
        arr_time: '2018-09-10 16:07',
        seats: 8
        },
        {
        dep_loc: 'Göteborg',
        arr_loc: 'Stockholm',
        dep_time: '2018-09-10 15:04',
        arr_time: '2018-09-10 19:07',
        seats: 4
        }
    ];

    var view = {
        render: function () {
            matchedRides.forEach(ride => view.renderRow(ride));
        },
        renderRow: function(ride) {
            var tbody = document.getElementById('join-table');
            var elems = Object.values(ride);
            var tr = document.createElement("tr");

            for (var i = 0 ; i < elems.length; i++) {
                var td = document.createElement("td");
                var txt = document.createTextNode(elems[i]);
                td.appendChild(txt);
                tr.appendChild(td);
            }

            var secondlasttd = view.createDropDown();

            tr.appendChild(secondlasttd);

            var lasttd = view.createJoinBtn(elems);

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
                base.rest.getUser().then(function(user) {
                    // if(base.rest.isBusy(user.id, from, to) alert('You are already scheduled in tnis time interval')
                    // else joinRide(user.id, ride.id); alert('Ride successfully joined');
                });
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
            })

            document.getElementById("search-rides").onclick = function (event){
            document.getElementById("matched-rides").style.visibility = "visible";
            view.render();
            }
        }
    };
    return controller;
};