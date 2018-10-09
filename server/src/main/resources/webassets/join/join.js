var base = base || {};
base.joinRideController = function() {
    var matchedRides = [
        {
        dep_loc: 'Malmö',
        arr_loc: 'Lund',
        dep_time: '11:03',
        arr_time: '12:03',
        date: '2018-09-10',
        seats: 4
        },
        {
        dep_loc: 'Lund',
        arr_loc: 'Malmö',
        dep_time: '15:04',
        arr_time: '16:07',
        date: '1995-03-13',
        seats: 8
        },
        {
        dep_loc: 'Göteborg',
        arr_loc: 'Stockholm',
        dep_time: '15:04',
        arr_time: '19:07',
        date: '2018-03-19',
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

            var lasttd = view.createJoinBtn();

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
        createJoinBtn: function () {
            var lasttd = document.createElement("td");
            lasttd.id = 'last-td';
            var btn = document.createElement("button");
            btn.className = "btn btn-success";
            btn.textContent = "Join";
            lasttd.style.background = '#f5f5f5';
            lasttd.style.borderTop = 'none';

            btn.onclick = function(event) {
                alert('Ride successfully joined');
            };

            lasttd.appendChild(btn);

            return lasttd;
        }
    };

    for(var i; i < 100; i++) {
        var tr = document.createElement("tr");
        tr.id= i;
    }
    var controller = {
        load: function () {
            var select_from = document.getElementById("select-from");
            var select_to = document.getElementById("select-to");
            var myobject = {
                Malmö : 'Malmö',
                Lund : 'Lund',
                Stockholm : 'Stockholm',
                Göteborg : 'Göteborg'
            }
            for(index in myobject) {
                select_to.options[select_to.options.length] = new Option(myobject[index], index);
            }

            for(index in myobject) {
                select_from.options[select_from.options.length] = new Option(myobject[index], index);
            }

            document.getElementById("search-rides").onclick = function (event){
            document.getElementById("matched-rides").style.visibility = "visible";
            view.render();
            }
        }
    };
    return controller;
};