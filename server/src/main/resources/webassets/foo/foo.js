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
        // {
        // role: 'Passenger',
        // dep_loc: 'Malmö',
        // arr_loc: 'Lund',
        // dep_time: '11:03',
        // arr_time: '12:03',

        // },
        // {
        // role: 'Passenger',
        // dep_loc: 'Lund',
        // arr_loc: 'Malmö',
        // dep_time: '15:04',
        // arr_time: '16:07',

        // },
        // {
        // role: 'Driver',
        // dep_loc: 'Göteborg',
        // arr_loc: 'Stockholm',
        // dep_time: '15:04',
        // arr_time: '19:07',

        // }];

        var modal = [
            {
            name: 'Anders',
            role: 'Driver',
            },
            {
            name: 'Kalle',
            role: 'Passenger',
            },
            {
            name: 'Bosse',
            role: 'Passenger',
            },
            {
            name: 'Pelle',
            role: 'Passenger',
            }
          ];

    var view = {
        // Creates HTML for each foo in model
        render: function() {
            model.forEach(ride => view.renderRow(ride));
            modal.forEach(function(value, i){
              view.modalModel(value, i);


            });
        },





        // Creates HTML for foo parameter and adds it to the parent of the template

    renderRow: function(ride) {
    var tbody = document.getElementById('remove-table');
    var elems = Object.values(ride);
    var tr = document.createElement("tr");
    tr.id="test";

    for (var i = 0 ; i < elems.length; i++) {
        var td = document.createElement("td");
        var txt = document.createTextNode(elems[i]);
        td.appendChild(txt);
        tr.appendChild(td);
    }
    var isDriver = elems[0] === 'Driver';

    if(isDriver) {
      var lasttd = view.createBtn('Delete');
    }
     else {
       var lasttd = view.createBtn('Leave');
     }

     var infoButton = view.createInfoBtn('More info');



    tr.appendChild(lasttd);
    tr.appendChild(infoButton);

    tbody.appendChild(tr);

    infoButton.onclick = function () {
    //  $(".test").attr("data-toggle", "modal");
      $("#userModal").modal()

    }
},

modalModel: function(tableElems, index){
    var tbody = document.getElementById('modal-table');
    var elems = Object.values(tableElems);
    var tr = document.createElement("tr");

    for (var i = 0 ; i < elems.length; i++) {
        var td = document.createElement("td");
        var txt = document.createTextNode(elems[i]);
        td.appendChild(txt);
        tr.appendChild(td);
    }
      console.log(index);

    base.rest.getUser().then(function(user) {
      for(var k = 0; k < index + 1; k++){
      tbody.appendChild(tr);
    }
    if(user.isAdmin()) {
      var lasttd = view.createModalBtn('Ban');
    } else if(model[k].role === 'Driver') {
      var lasttd = view.createModalBtn('Kick');
    }
    tr.appendChild(lasttd);
    });
},

createModalBtn: function (btnString) {
        var secondlasttd = document.createElement("td");
        secondlasttd.id = 'last-td';
        var btn = document.createElement("button");
        btn.className = "btn btn-danger";
        btn.textContent = btnString;
        secondlasttd.style.background = '#f5f5f5';
        secondlasttd.style.borderTop = 'none';

        if(btnString === 'Kick') {
          btn.onclick = function(event) {
              alert('User successfully kicked');
          };
        } else {
          btn.onclick = function(event) {
              alert('User successfully banned');
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


createBtn: function (btnString) {
        var secondlasttd = document.createElement("td");
        secondlasttd.id = 'last-td';
        var btn = document.createElement("button");
        btn.className = "btn btn-danger";
        btn.textContent = btnString;
        secondlasttd.style.background = '#f5f5f5';
        secondlasttd.style.borderTop = 'none';

        if(btnString === 'Delete') {
          btn.onclick = function(event) {
              alert('Ride successfully deleted');
          };
        } else {
          btn.onclick = function(event) {
              alert('Left ride successfully');
          };
        }

        secondlasttd.appendChild(btn);

        return secondlasttd;
    }

/*
        <td>1</td>
        <td>2</td>
        <td>3</td>
        <td>4</td>
        <td>5</td>

        <td><button id="delete-user" type="button" class="w-100 btn btn-danger" onclick = "removeThisRow(this.parentElement.parentElement)">Delete</button></td>

*/



    };



    var controller = {
        load: function() {
            base.rest.getUser().then(function(user) {
                if(user.id === 1) {
                    base.rest.getRides().then(function(rides) {
                        model = rides;
                        return model;
                    });
                } else {
                    base.rest.getUserRides(user.id).then(function(rides) {
                        model = rides;
                        return model;
                    });
                }
            });
            // Adds callback to the form.
            document.getElementById('foo-form').onsubmit = function(event) {
                event.preventDefault();
                controller.submitFoo();
                return false;
            };
            // Loads all foos from the server through the REST API, see res.js for definition.
            // It will replace the model with the foos, and then render them through the view.
            base.rest.getFoos().then(function(foos) {
                //model = foos;
                view.render();
            });
        }

    };

    return controller;
};
