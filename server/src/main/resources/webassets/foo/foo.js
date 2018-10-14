/*
 * Model/view/controller for the foo tab.
 * Author: Rasmus Ros, rasmus.ros@cs.lth.se
 */
var base = base || {};
// Defines the base namespace, if not already declared. Through this pattern it doesn't matter which order
// the scripts are loaded in.




base.fooController = function() {

    // List of all foo data, will be useful to have when update functionality is added.
    var model = [];

    var view = {
        // Creates HTML for each ride in model
        render: function() {
            model.forEach(ride => view.renderRow(ride));
            model.forEach(ride => view.modalModel(ride));
        },
    renderRow: function(ride) {
    var tbody = document.getElementById('remove-table');
    base.rest.getUser().then(function(user) {
        var tr = document.createElement("tr");

        var td1 = document.createElement("td");
        if(user.id === 1) {
            var txt = document.createTextNode("Admin");
            td1.appendChild(txt);
            tr.appendChild(td1);
        } else {
            var txt = document.createTextNode(ride.role);
            td1.appendChild(txt);
            tr.appendChild(td1);
        }
    
        var td2 = document.createElement("td");
        var txt = document.createTextNode(ride.departureLocation);
        td2.appendChild(txt);
        tr.appendChild(td2);
    
        var td3 = document.createElement("td");
        var txt = document.createTextNode(ride.arrivalLocation);
        td3.appendChild(txt);
        tr.appendChild(td3);

        var td5 = document.createElement("td");
        var txt = document.createTextNode(ride.departureTime.substring(0,16));
        td5.appendChild(txt);
        tr.appendChild(td5);
    
        var td4 = document.createElement("td");
        var txt = document.createTextNode(ride.arrivalTime.substring(0,16));
        td4.appendChild(txt);
        tr.appendChild(td4);

        if(user.id === ride.driverId || user.id === 1) {
            var lasttd = view.createBtn('Delete', tr, ride);
            tr.appendChild(lasttd);
        }
        else {
             var lasttd = view.createBtn('Leave', tr, ride);
             tr.appendChild(lasttd);
        }

        var infoButton = view.createInfoBtn('More info');

        tr.appendChild(infoButton);
    
        tbody.appendChild(tr);
    
        infoButton.onclick = function () {
        //  $(".test").attr("data-toggle", "modal");
          $("#userModal").modal()
        
        }
    });
},
modalModel: function(ride){
    var tbody = document.getElementById('modal-table');

    var current_user = undefined;
    base.rest.getUser().then(function(user){
        current_user = user;
    });

    base.rest.getAllTravelers(ride.id).then(function(travellers) {
        travellers.forEach(function(t) {
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            base.rest.getSpecificUser(t.userId).then(function(user) {
            var txt = document.createTextNode(user.username);
            td.appendChild(txt);
            tr.appendChild(td);

            var td1 = document.createElement("td");
            var txt1 = document.createTextNode(t.role);
            td1.appendChild(txt1);
            tr.appendChild(td1);

            base.rest.getUser().then(function(user) {
                if(t.userId !== user.id) {
                    if(user.id === 1) {
                        var lasttd = view.createModalBtn('Ban');
                        tr.appendChild(lasttd);
                    } else if(ride.driverId  === current_user.id) {
                        var lasttd = view.createModalBtn('Kick', t, ride, tr);
                        tr.appendChild(lasttd);
                    }
                }
            });
            tbody.append(tr);
            });
        });
    });
},
createModalBtn: function (btnString, t, ride, tr) {
        var tbody = document.getElementById('modal-table');
        var secondlasttd = document.createElement("td");
        secondlasttd.id = 'last-td';
        var btn = document.createElement("button");
        btn.className = "btn btn-danger";
        btn.textContent = btnString;
        secondlasttd.style.borderTop = 'none';

        if(btnString === 'Kick') {
          btn.onclick = function(event) {
              tbody.removeChild(tr);
              base.rest.leaveRide(ride.id, t.userId).then(function() {
                  alert('User successfully kicked');
              });
          }
        } else {
          btn.onclick = function(event) {
              alert('Ban is not implemented in backend');
          };
        }

        secondlasttd.appendChild(btn);

        return secondlasttd;
    },
createInfoBtn: function () {
            var lasttd = document.createElement("td");
            lasttd.id = 'last-td';
            var btn = document.createElement("button");
            btn.className = "btn btn-info";
            btn.textContent = "More Info";
            lasttd.style.background = '#f5f5f5';
            lasttd.style.borderTop = 'none';

            lasttd.appendChild(btn);

            return lasttd;
        },
createBtn: function (btnString, tr, ride) {
        var secondlasttd = document.createElement("td");
        secondlasttd.id = 'last-td';
        var btn = document.createElement("button");
        btn.className = "btn btn-danger";
        btn.textContent = btnString;
        secondlasttd.style.background = '#f5f5f5';
        secondlasttd.style.borderTop = 'none';

        if(btnString === 'Delete') {
          btn.onclick = function(event) {
            base.rest.deleteRide(ride.id).then(function(response) {
                if(response.message === 'Ride not found') {
                  alert("Ride does not exist");
                } else {
                  var tbody = document.getElementById("remove-table");
                  tbody.removeChild(tr);
                  alert('Ride successfully deleted');
                }
            });
          };
        } else {
          btn.onclick = function(event) {
              base.rest.getUser().then(function(user) {
                base.rest.leaveRide(ride.id, user.id).then(function() {
                    var tbody = document.getElementById("remove-table");
                    tbody.removeChild(tr);
                    alert('Left ride successfully');
                  });
              });
          };
        }

        secondlasttd.appendChild(btn);

        return secondlasttd;
    }
};

    var controller = {
        load: function() {

            base.rest.getUser().then(function(user) {
                if(user.id === 1) {
                    base.rest.getRides().then(function(rides) {
                        model = rides;
                        model.forEach(ride => view.renderRow(ride));
                        model.forEach(function(value, i){
                          view.modalModel(value, i);
                        });
                        return model;
                    });
                } else {
                    base.rest.getUserRides(user.id).then(function(rides) {
                        model = rides;
                        model.forEach(ride => view.renderRow(ride));
                        model.forEach(function(value, i){
                          view.modalModel(value, i);
                        });
                        return model;
                    });
                }
            });

            view.render();
        }

    };

    return controller;
};
